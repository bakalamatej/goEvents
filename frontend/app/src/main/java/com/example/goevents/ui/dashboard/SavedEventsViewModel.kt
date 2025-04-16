package com.example.goevents.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SavedEventsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Saved Events Fragment"
    }
    val text: LiveData<String> = _text
}