package bruteForce.dm;

import java.util.LinkedList;
import java.util.Queue;

public class CandidatesQueue {
    private Queue<CandidateToDecode> totalCandidateStringsToDecode; //product of all tasks

    public CandidatesQueue() {
        this.totalCandidateStringsToDecode = new LinkedList<>();
    }

    synchronized void addToQueue(CandidateToDecode candidateToDecode) {
        totalCandidateStringsToDecode.add(candidateToDecode);
    }

    public Boolean isQueueEmpty() {
        if (totalCandidateStringsToDecode.isEmpty()) {
            return true;
        }
        return false;
    }

    public CandidateToDecode pollFromQueue() {
        synchronized (totalCandidateStringsToDecode) {
            return totalCandidateStringsToDecode.poll();
        }
    }

    public void clearQueue(){
        totalCandidateStringsToDecode.clear();
    }

}
