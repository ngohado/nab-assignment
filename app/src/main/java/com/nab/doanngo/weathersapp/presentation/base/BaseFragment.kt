package com.nab.doanngo.weathersapp.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedDispatcherOwner
import androidx.activity.addCallback
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    lateinit var binding: VB
        private set
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            initData(arguments)
        }
    }

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = bindingInflater.invoke(inflater, container, false)
        this.binding = binding

        initViews()
        initActions()

        return binding.root
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()

        (requireActivity() as OnBackPressedDispatcherOwner)
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner) {
                onBackPressed()
            }
    }

    /**
     * Initial data for screen just one times. It also isn't called when configuration changed.
     *
     * @param data [Bundle]: Composite received data.
     */
    abstract fun initData(data: Bundle?)

    /**
     * Init views in screen (e.g., a adapter, layout manager for RecycleView).
     */
    abstract fun initViews()

    /**
     * Declare listener on views (e.g., listen click on button, view)
     */
    abstract fun initActions()

    /**
     * Observe states from ViewModel to update views.
     * Make sure that this method is called after [initViews] because the views are need to ready to
     * display data.
     */
    abstract fun initObservers()

    /**
     * Called when user click system back button
     */
    protected fun onBackPressed() {
        // Lets child fragment handle
    }
}
