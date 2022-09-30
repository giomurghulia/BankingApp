package com.example.bankingapp.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class SecurityViewModel : ViewModel() {
    private val passCode = "0934"
    private var inputPassCode = ""

    private val _state = MutableSharedFlow<List<PassListItem>>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val state get() = _state.asSharedFlow()

    private val _action = MutableSharedFlow<Boolean>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val action get() = _action.asSharedFlow()

    private val inputList = mutableListOf<PassListItem>()

    init {
        createPassList()
    }

    fun onItemClick(itemId: Int) {
        when (itemId) {
            10 -> {}
            11 -> {
                val index = inputList.indexOfLast { it.isChecked }
                inputList[index] = PassListItem(false)

                inputPassCode = inputPassCode.substring(0, inputPassCode.length - 1)

            }
            else -> {
                inputPassCode += itemId.toString()

                val index = inputList.indexOfFirst { !it.isChecked }
                inputList[index] = PassListItem(true)

            }
        }

        _state.tryEmit(inputList)
        checkedPassCode()
    }

    private fun checkedPassCode() {
        if (inputPassCode == passCode) {
            _action.tryEmit(true)

            inputList.clear()
            inputPassCode = ""

            createPassList()
        } else if (inputPassCode.length == 4) {
            _action.tryEmit(false)

            inputPassCode = ""
            inputList.clear()

            createPassList()
        }
    }

    private fun createPassList() {
        inputList.add(PassListItem(false))
        inputList.add(PassListItem(false))
        inputList.add(PassListItem(false))
        inputList.add(PassListItem(false))

        _state.tryEmit(inputList)
    }
}