package com.lambui.healthcare_doctor.base

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lambui.healthcare_doctor.utils.liveData.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

 abstract class BaseViewModel : ViewModel() {
  /**
   * This is the job for all coroutines started by this ViewModel.
   *
   * Cancelling this job will cancel all coroutines started by this ViewModel.
   */
  private val viewModelJob: Job? by lazy { Job() }

  val isLoading = SingleLiveEvent<Boolean>()
  val onError = SingleLiveEvent<Throwable>()
  val showError = MutableLiveData<String>()
  val typeToolbar = MutableLiveData<Int>()
  val titleToolbar = MutableLiveData<String>()

  fun setTypeAndTitleToolbar(valueType: Int?, valueTitle: String?) {
    valueType?.let {
      typeToolbar.value = valueType
    }
    valueTitle?.let {
      titleToolbar.value = valueTitle
    }
  }

  fun showError(error: String?) {
    Log.d("showError", error ?: "null")
    showError.value = error
  }

  /**
   * This is the scope for all coroutines launched by [ViewModel].
   *
   * Since we pass [viewModelJob], you can cancel all coroutines launched by [viewModelScope] by calling
   * viewModelJob.cancel().  This is called in [onCleared].
   */
  private val viewModelScope: CoroutineScope? by lazy {
    CoroutineScope(Dispatchers.Main + viewModelJob as Job)
  }

  private val compositeDisposable = CompositeDisposable()

  fun launchDisposable(job: () -> Disposable) {
    compositeDisposable.add(job())
  }

  override fun onCleared() {
    super.onCleared()
    compositeDisposable.clear()
    if (viewModelScope != null) {
      viewModelJob?.cancel()
    }
  }
}