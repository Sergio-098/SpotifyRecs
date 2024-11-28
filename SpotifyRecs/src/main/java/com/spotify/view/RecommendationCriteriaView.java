package com.spotify.view;

import com.spotify.interface_adapter.generate.RecommendationCriteriaViewModel;

import javax.swing.*;

public class RecommendationCriteriaView extends JPanel {
    public RecommendationCriteriaView(RecommendationCriteriaViewModel recommendationCriteriaViewModel) {
    }

    public String getViewName() {
        return "recommendation criteria";
    }
}
