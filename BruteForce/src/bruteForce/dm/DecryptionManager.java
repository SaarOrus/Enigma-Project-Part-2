package bruteForce.dm;

import Utilies.LogicUtilities;
import bruteForce.DictionaryDecipher;
import engine.Engine;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static java.util.concurrent.TimeUnit.SECONDS;

public class DecryptionManager {
    //engine
    private final Engine engine;
    //agents
    private int numberOfAgents;
    //threads
    private BlockingQueue<Runnable> blockingQueue;
    private ThreadPoolExecutorTasks executor;// all candidate strings to decode from all agents
    //DM details
    private DifficultyLevels difficultyLevel;
    private int taskSize; //for each agent
    //decode details
    private int totalNumberOfTasksToPerformed;
    private long progressInLevel;
    private double avgTimeForAllTasks;
    private String stringToDecode;
    private static Long timeOfExecutionAllTasks; // total time to execute all tasks
    private long amountOfPossibleCodesToCheck; //amount of code options by level
    private CandidatesQueue totalCandidateStringsToDecode; //product of all tasks
    private final DictionaryDecipher dictionaryDecipher;
    private long progressAllLevels=0;
    private boolean isFinish=false;
    private static boolean isPause=false;

    public static final Object pauseLock=new Object();

    private static final int MAX_TASKS_ON_QUEUE = 1000;

    //ctor
    public DecryptionManager(Engine engine, DictionaryDecipher dictionaryDecipher){
        this.engine=engine;
        this.dictionaryDecipher=dictionaryDecipher;
        this.progressInLevel=0;
        this.totalCandidateStringsToDecode=new CandidatesQueue();
        this.totalNumberOfTasksToPerformed=0;
        this.avgTimeForAllTasks=0;
    }

    public boolean isCandidatesEmpty(){
        return totalCandidateStringsToDecode.isQueueEmpty();
    }

    public void setDmByUser(int numberOfAgents,DifficultyLevels difficultyLevel,int taskSize,String stringToDecode){
        this.numberOfAgents=numberOfAgents;
        this.difficultyLevel=difficultyLevel;
        this.taskSize=taskSize;
        this.stringToDecode=stringToDecode;
        this.timeOfExecutionAllTasks=0L;
        this.blockingQueue=new LinkedBlockingQueue<>(MAX_TASKS_ON_QUEUE);
        this.executor=new ThreadPoolExecutorTasks(numberOfAgents,numberOfAgents,5,SECONDS, blockingQueue);
    }

    public CandidatesQueue getTotalCandidateStringsToDecode() {
        return totalCandidateStringsToDecode;
    }

    public boolean isFinish(){
        return isFinish;
    }

    public boolean isPause(){
        return isPause;
    }

    public void setFinish(boolean finish) {
        isFinish = finish;
    }

    public void setPause(boolean pause) {
        isPause = pause;
    }

    public int getTotalNumberOfTasksToPerformed() {
        return totalNumberOfTasksToPerformed;
    }

    public void updateTotalNumberOfTasksToPerformed(){
        switch (difficultyLevel) {
            case EASY:
                this.amountOfPossibleCodesToCheck = calculateAmountOfDerivedCodesEasyLevel();
                setTotalNumberOfTasksToPerformed();
                break;
            case MEDIUM:
                this.amountOfPossibleCodesToCheck = calculateAmountOfDerivedCodesMediumLevel();
                setTotalNumberOfTasksToPerformed();
                break;
            case HARD:
                this.amountOfPossibleCodesToCheck = calculateAmountOfDerivedCodesHardLevel();
                setTotalNumberOfTasksToPerformed();
                break;
            case IMPOSSIBLE:
                this.amountOfPossibleCodesToCheck = calculateAmountOfDerivedCodesImpossibleLevel();
                setTotalNumberOfTasksToPerformed();
                break;
        }
    }

    //execute by level
    public void executeByDifficultyLevel() throws InterruptedException {
        progressAllLevels=0;

        switch (difficultyLevel) {
            case EASY:
                progressInLevel=0;
                executeEasyLevel(engine.getMachineCode().getReflectorGreekNumber(),engine.getCurrentCode().getDtoCode().getRotorsNumbers());
                break;
            case MEDIUM:
                progressInLevel=0;
                executeMediumLevel(engine.getCurrentCode().getDtoCode().getRotorsNumbers());
                break;
            case HARD:
                progressInLevel=0;
                executeHardLevel(engine.getCurrentCode().getDtoCode().getRotorsNumbers());
                break;
            case IMPOSSIBLE:
                progressInLevel=0;
                executeImpossibleLevel();
                break;
        }

        executor.shutdown();
    }

    public void stopDecoding(){
        executor.shutdownNow();

        blockingQueue.clear();
        this.executor=new ThreadPoolExecutorTasks(numberOfAgents,numberOfAgents,5,SECONDS, blockingQueue);
        totalCandidateStringsToDecode.clearQueue();
        isFinish=true;
    }

    public void resetTime(){
        timeOfExecutionAllTasks=0L;
        avgTimeForAllTasks=0;
    }

    public double getProgressAllLevels() {
        return ((double) (progressAllLevels) / (double) (totalNumberOfTasksToPerformed));
    }

    public long getProgressPercent() {
        return progressAllLevels*100/totalNumberOfTasksToPerformed;
    }

    public String addTasks(String startPosition, String reflectorGreekNumber, int[] rotorsArr) throws InterruptedException {
        AgentTask newTask = new AgentTask(taskSize, rotorsArr, reflectorGreekNumber, stringToDecode,
                dictionaryDecipher, startPosition, engine.getRepository(), totalCandidateStringsToDecode);
        progressAllLevels+=1;
        blockingQueue.put(newTask);
        executor.prestartAllCoreThreads();

        return newTask.getEndPos();
    }

    public void executeEasyLevel(String reflectorGreekNumber, int[] rotorsArr) throws InterruptedException {
        int numberOfCodesOptions = calculateAmountOfDerivedCodesEasyLevel();
        String startPosition = createStartPosition();

        int numberOfIteration=numberOfTaskEasy(numberOfCodesOptions);

        progressInLevel=0;
        do {
            startPosition = addTasks(startPosition,reflectorGreekNumber,rotorsArr);
            progressInLevel=progressInLevel+1;
        } while (progressInLevel < numberOfIteration && !isFinish);

    }

    public static boolean checkIfWait(){
        return isPause;
    }

    public void resume() {
        synchronized (pauseLock) {
            pauseLock.notifyAll();
        }
    }

    public static void setIsPause(boolean isPause) {
        DecryptionManager.isPause = isPause;
    }

    public void executeMediumLevel(int[] rotorsArr) throws InterruptedException {

        for (int i = 1; i <= engine.getRepository().getNumberOfReflectors(); i++) {
            progressInLevel=0;
            String reflectorGreekNumber = LogicUtilities.convertNumberToReflectorId(i);
            executeEasyLevel(reflectorGreekNumber,rotorsArr);
        }
    }

    public Long getTimeOfExecutionAllTasks() {
        return timeOfExecutionAllTasks;
    }

    public void executeHardLevel(int[] combinationOfRotors) throws InterruptedException {

        List<List<Integer>> result1 = LogicUtilities.permute(combinationOfRotors);

        for (List<Integer> curr : result1) {
            int[] rotorsArr = toIntArray(curr);
            executeMediumLevel(rotorsArr);
            progressInLevel=0;
        }

    }

    public void executeImpossibleLevel() throws InterruptedException {
        int numberOfRotorsInUse=engine.getRepository().getNumberOfRotorsInUse();
        int optionalRotorsID=engine.getRepository().getNumberOfRotors()-1;
        int[] combination = new int[numberOfRotorsInUse];

        // initialize with the lowest lexicographic combination
        for (int i = 0; i < numberOfRotorsInUse; i++) {
            combination[i] = i + 1;
        }

        while (combination[numberOfRotorsInUse - 1] < optionalRotorsID) {
            executeHardLevel(combination);
             progressInLevel=0;

            // generate next combination in lexicographic order
            int t = numberOfRotorsInUse - 1;
            while (t != 0 && combination[t] == optionalRotorsID - numberOfRotorsInUse + t) {
                t--;
            }
            combination[t]++;
            for (int i = t + 1; i < numberOfRotorsInUse; i++) {
                combination[i] = combination[i - 1] + 1;
            }
        }

    }

    public int[] toIntArray(List<Integer> intList){
        return intList.stream().mapToInt(Integer::intValue).toArray();
    }

    public int calculateAmountOfDerivedCodesEasyLevel() {
        int amountOfRotors = engine.getRepository().getNumberOfRotorsInUse();
        int numberOfLettersInTheLanguage = engine.getRepository().getKeyboard().getNumberOfCharsInKeyboard();
        return (int) Math.pow(numberOfLettersInTheLanguage, amountOfRotors);
    }
    public int calculateAmountOfDerivedCodesMediumLevel() {
        int reflectorOptions = engine.getRepository().getNumberOfReflectors();
        return reflectorOptions * calculateAmountOfDerivedCodesEasyLevel();
    }
    public int calculateAmountOfDerivedCodesHardLevel() {
        int amountOfRotors = engine.getRepository().getNumberOfRotorsInUse();
        int numberOfRotorPositionsInSlots = LogicUtilities.factorial(amountOfRotors);
        return numberOfRotorPositionsInSlots * calculateAmountOfDerivedCodesMediumLevel();
    }
    public long calculateAmountOfDerivedCodesImpossibleLevel() {
        int rotorsIdsOptions = engine.getRepository().getNumberOfRotors();
        int amountOfRotors = engine.getRepository().getNumberOfRotorsInUse();
        long numberOfRotorsOptions = LogicUtilities.binomial(rotorsIdsOptions,amountOfRotors)/5;
        return numberOfRotorsOptions * calculateAmountOfDerivedCodesHardLevel();
    }

    public String createStartPosition(){
        String firstLetterInTheLanguage = String.valueOf(engine.getRepository().getKeyboard().getKeyboardChars()[1]);

        return String.join("", Collections.nCopies(engine.getRepository().getNumberOfRotorsInUse(), firstLetterInTheLanguage));
    }

    public void setTotalNumberOfTasksToPerformed() {
        if (taskSize >= amountOfPossibleCodesToCheck) {
            this.totalNumberOfTasksToPerformed = 1;
        } else
            this.totalNumberOfTasksToPerformed = (int) (amountOfPossibleCodesToCheck / taskSize);
    }

    public int numberOfTaskEasy(int amountOfPossibleCodes) {
        int numberOfIteration;

        if (taskSize >= amountOfPossibleCodes) {
            numberOfIteration = 1;
        } else
            numberOfIteration = amountOfPossibleCodes / taskSize;

        return numberOfIteration;
    }

    public static void addToTotalTime(long time){
        timeOfExecutionAllTasks+=time;
    }

    public double getAvgTimeForAllTasks() {
        return avgTimeForAllTasks;
    }

    public void calculateAverageTimeToTask(){
        avgTimeForAllTasks= (double)(timeOfExecutionAllTasks)/(double)(totalNumberOfTasksToPerformed);
    }
}
