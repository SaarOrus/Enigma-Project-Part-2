package components;

import java.util.HashMap;
import java.util.Map;

public class PlugBoard {
    //Key-A character the user typed
    //Value-The plug that connects to this key
    private HashMap<Character, Character> plugBoardConnections;

    public PlugBoard(PlugBoard plugBoard) {
        HashMap<Character, Character> copyHashMap=new HashMap<>();
        for (HashMap.Entry<Character, Character> entry : plugBoard.plugBoardConnections.entrySet()) {
            copyHashMap.put(entry.getKey(),entry.getValue());
        }
        this.plugBoardConnections=copyHashMap;
    }
    public PlugBoard(HashMap < Character, Character > plugMap) {
            plugBoardConnections = plugMap;
            duplicatePlugInPlugMap();
        }


    private void duplicatePlugInPlugMap() {
        HashMap<Character, Character> duplicatePlugBoardConnections= new HashMap<>(plugBoardConnections);

        for(Map.Entry<Character, Character> entry : plugBoardConnections.entrySet()) {
            duplicatePlugBoardConnections.put(entry.getValue(), entry.getKey());
        }

        this.plugBoardConnections=duplicatePlugBoardConnections;
    }

    public char matchConnectedPlug (char entryConnectionPlug){
        Character matchPlug= plugBoardConnections.get(entryConnectionPlug);
        if(matchPlug!=null)
        {
            return matchPlug;
        }
        return entryConnectionPlug;
    }
}