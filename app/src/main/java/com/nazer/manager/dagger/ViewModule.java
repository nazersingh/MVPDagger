package com.nazer.manager.dagger;

import com.nazer.ui.dialogues.DateTimePickerDialogue;
import com.nazer.ui.dialogues.Dialogues;
import com.nazer.ui.dialogues.ImageVideoAudioPicker;
import com.nazer.util.LocationHelper;
import com.nazer.util.Utility;
import com.nazer.util.Validations;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ViewModule {

    @Provides
    @Singleton
    public Dialogues getDialogue() {
        return new Dialogues();
    }

    @Provides
    @Singleton
    public ImageVideoAudioPicker getImageVideoAudioPicker() {
        return new ImageVideoAudioPicker();
    }

    @Provides
    @Singleton
    public DateTimePickerDialogue getTimePicker() {
        return new DateTimePickerDialogue();
    }

    @Provides
    @Singleton
    public Utility getUtility() {
        return new Utility();
    }

    @Provides
    @Singleton
    public Validations getValidations() {
        return new Validations();
    }

    @Provides
    @Singleton
    public LocationHelper getLocation() {
        return new LocationHelper();
    }


}
