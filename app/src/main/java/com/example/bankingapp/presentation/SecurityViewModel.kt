package com.example.bankingapp.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

class SecurityViewModel : ViewModel() {
    private val passCode = "0934"
    private var inputPassCode = ""

    private val _state = MutableStateFlow(createFirstList())
    val state get() = _state.asStateFlow()

    private val _action = MutableSharedFlow<Boolean>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val action get() = _action.asSharedFlow()

    private val inputList = mutableListOf<PassListItem>()

    init {
        clearInputList()
    }

    fun onItemClick(itemId: Int) {
        when (itemId) {
            10 -> {}
            11 -> {
                if (inputPassCode.isNotEmpty()) {
                    val index = inputList.indexOfLast { it.isChecked }
                    inputList[index] = PassListItem(false)

                    inputPassCode = inputPassCode.substring(0, inputPassCode.length - 1)
                }
            }
            else -> {
                inputPassCode += itemId.toString()

                val index = inputList.indexOfFirst { !it.isChecked }
                inputList[index] = PassListItem(true).copy()
            }
        }

        _state.value = inputList.toList()
        checkedPassCode()
    }

    private fun checkedPassCode() {
        if (inputPassCode == passCode) {
            _action.tryEmit(true)

            clearInputList()
            _state.value = inputList.toList()

        } else if (inputPassCode.length == passCode.length) {
            _action.tryEmit(false)

            clearInputList()
            _state.value = inputList.toList()
        }
    }

    private fun clearInputList() {
        inputPassCode = ""
        inputList.clear()

        inputList.add(PassListItem(false))
        inputList.add(PassListItem(false))
        inputList.add(PassListItem(false))
        inputList.add(PassListItem(false))
    }

    private fun createFirstList(): List<PassListItem> {
        val list = mutableListOf<PassListItem>()

        list.add(PassListItem(false))
        list.add(PassListItem(false))
        list.add(PassListItem(false))
        list.add(PassListItem(false))

        return list
    }
}