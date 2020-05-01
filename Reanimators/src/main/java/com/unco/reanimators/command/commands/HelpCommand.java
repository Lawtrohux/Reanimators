package com.unco.reanimators.command.commands;

import com.unco.reanimators.Reanimators;
import com.unco.reanimators.command.Command;
import com.unco.reanimators.command.syntax.SyntaxChunk;
import com.unco.reanimators.module.ModuleManager;

import java.util.Arrays;

public class HelpCommand extends Command {

    private static final Subject[] subjects = new Subject[]{
            new Subject(
                    new String[]{"type","int","boolean","double","float"},
                    new String[]{
                        "Every module has a value, and that value is always of a certain &btype.\n",
                        "These types are displayed in Reanimators as the ones java use. They mean the following:",
                        "&bboolean&r: Enabled or not. Values &3true/false",
                        "&bfloat&r: A number with a decimal point",
                        "&bdouble&r: Like a float, but a more accurate decimal point",
                        "&bint&r: A number with no decimal point"
                    }
            )
    };
    private static String subjectsList = "";
    static {
        for (Subject subject : subjects)
            subjectsList += subject.names[0] + ", ";
        subjectsList = subjectsList.substring(0, subjectsList.length()-2);
    }

    public HelpCommand() {
        super("help", new SyntaxChunk[]{});
        setDescription("Delivers help on certain subjects. Use &b-help subjects&r for a list.");
    }

    @Override
    public void call(String[] args) {
        if (args[0] == null) {
            Command.sendStringChatMessage(new String[]{
                    "Reanimators Help:",
                    "Press " + ModuleManager.getModuleByName("ClickGUI").getBindName() + "&4 to open GUI",
                    "For change prefix write .prefix (character)",
                    "For some other command talk with devs!"
            });
        }
    }

    private static class Subject {
        String[] names;
        String[] info;

        public Subject(String[] names, String[] info) {
            this.names = names;
            this.info = info;
        }
    }
}
