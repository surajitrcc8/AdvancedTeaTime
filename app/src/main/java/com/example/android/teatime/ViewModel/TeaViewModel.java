package com.example.android.teatime.ViewModel;

import com.example.android.teatime.model.Tea;

public class TeaViewModel {

    private Tea tea;

    public TeaViewModel(Tea tea) {
        this.tea = tea;
    }

    public String getTeaName() {
        return tea.getTeaName();
    }

    public int getTeaImage() {
        return tea.getImageResourceId();
    }
}
