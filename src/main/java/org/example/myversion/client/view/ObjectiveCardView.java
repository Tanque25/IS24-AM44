package org.example.myversion.client.view;

import org.example.myversion.server.model.decks.cards.ObjectiveCard;
import org.example.myversion.server.model.decks.cards.PatternObjectiveCard;
import org.example.myversion.server.model.decks.cards.ResourceObjectiveCard;
import org.example.myversion.server.model.decks.cards.SpecialObjectiveCard;
import org.example.myversion.server.model.enumerations.Resource;
import org.example.myversion.server.model.enumerations.SpecialObject;

public class ObjectiveCardView {

    public void displayObjectiveCardTopLine(ObjectiveCard objectiveCard) {
        System.out.print("┌―――――――┐");
    }

    public void displayObjectiveCardMiddleLine(ObjectiveCard objectiveCard) {
        System.out.print("objective");
    }

    public void displayObjectiveCardBottomLine(ObjectiveCard objectiveCard) {
        System.out.print("└―――――――┘");
    }

    public void displayObjectiveCardMiddleLine(ResourceObjectiveCard resourceObjectiveCard) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(resourceObjectiveCard.getCardPoints());
        stringBuilder.append("  ");
        for(Resource resource : resourceObjectiveCard.getObjective()) {
            stringBuilder.append(resource.getShortName());
        }
        stringBuilder.append("  │");

        System.out.print(stringBuilder);
    }

    public void displayObjectiveCardMiddleLine(SpecialObjectiveCard specialObjectiveCard) {
        StringBuilder stringBuilder = new StringBuilder();
        int objectiveSize = specialObjectiveCard.getObjective().length;

        switch (objectiveSize) {
            case 2 -> {
                stringBuilder.append(specialObjectiveCard.getCardPoints());
                stringBuilder.append("   ");
                for(SpecialObject specialObject : specialObjectiveCard.getObjective()) {
                    stringBuilder.append(specialObject.getShortName());
                }
                stringBuilder.append("  │");
            }
            case 3 -> {
                stringBuilder.append(specialObjectiveCard.getCardPoints());
                stringBuilder.append("  ");
                for(SpecialObject specialObject : specialObjectiveCard.getObjective()) {
                    stringBuilder.append(specialObject.getShortName());
                }
                stringBuilder.append("  │");
            }
        }

        System.out.print(stringBuilder);
    }

    public void displayObjectiveCardTopLine(PatternObjectiveCard patternObjectiveCard) {
        StringBuilder stringBuilder = new StringBuilder();
        Resource[] topLineObjective = patternObjectiveCard.getObjective()[0];

        stringBuilder.append("┌――");

        for (Resource resource : topLineObjective) {
            stringBuilder.append(resource != null ? resource.getShortName() : "―");
        }

        stringBuilder.append("――┐");

        System.out.print(stringBuilder);
    }

    public void displayObjectiveCardMiddleLine(PatternObjectiveCard patternObjectiveCard) {
        StringBuilder stringBuilder = new StringBuilder();
        Resource[] middleLineObjective = null;

        if (patternObjectiveCard.getId() == 91 || patternObjectiveCard.getId() == 92)
            middleLineObjective = patternObjectiveCard.getObjective()[2];
        else
            middleLineObjective = patternObjectiveCard.getObjective()[1];

        stringBuilder.append(patternObjectiveCard.getCardPoints());
        stringBuilder.append("  ");

        for (Resource resource : middleLineObjective) {
            stringBuilder.append(resource != null ? resource.getShortName() : " ");
        }

        stringBuilder.append("  │");

        System.out.print(stringBuilder);
    }

    public void displayObjectiveCardBottomLine(PatternObjectiveCard patternObjectiveCard) {
        StringBuilder stringBuilder = new StringBuilder();
        Resource[] bottomLineObjective = null;
        if(patternObjectiveCard.getId() >= 87 && patternObjectiveCard.getId() <= 90)
            bottomLineObjective = patternObjectiveCard.getObjective()[2];
        else if (patternObjectiveCard.getId() >= 91 && patternObjectiveCard.getId() <= 94)
            bottomLineObjective = patternObjectiveCard.getObjective()[3];

        stringBuilder.append("└――");

        assert bottomLineObjective != null;
        for (Resource resource : bottomLineObjective) {
            stringBuilder.append(resource != null ? resource.getShortName() : "―");
        }

        stringBuilder.append("――┘");

        System.out.print(stringBuilder);
    }

}
