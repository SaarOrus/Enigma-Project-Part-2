package bruteForce.dm;

import dtoHandling.MachineCode;

// This class present every string which is candidate to decode by specific agent
public class CandidateToDecode {
    private final MachineCode myConfigurationOfCode; // the current code which the candidate string found in
    private final String myAgentId; // the agent id who found the candidate string
    private final String candidateStringToDecode; // the string that was discovered

    //ctor
    public CandidateToDecode(MachineCode myConfigurationOfCode,String myAgentId,String candidateStringToDecode){
        this.myConfigurationOfCode=myConfigurationOfCode;
        this.myAgentId=myAgentId;
        this.candidateStringToDecode=candidateStringToDecode;
    }

    @Override
    public String toString() {
        String candidateToString = "Agent ID: " + myAgentId +
                " | Candidate to decode: " + candidateStringToDecode + "  | " +
                myConfigurationOfCode.toString();

        return candidateToString;
    }
}
