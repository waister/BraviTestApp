package br.com.waister.bravitestapp.features.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import br.com.waister.bravitestapp.databinding.FragmentHomeBinding
import br.com.waister.bravitestapp.models.ActivityResponse
import br.com.waister.bravitestapp.utils.ACTIVITY_TYPES
import br.com.waister.bravitestapp.utils.STATUS_ABORTED
import br.com.waister.bravitestapp.utils.STATUS_FINISHED
import br.com.waister.bravitestapp.utils.STATUS_STARTED
import br.com.waister.bravitestapp.utils.ViewState
import br.com.waister.bravitestapp.utils.hide
import br.com.waister.bravitestapp.utils.isVisible
import br.com.waister.bravitestapp.utils.show
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.activity.observe(viewLifecycleOwner) { event ->
            onLoading(false)
            when (event) {
                is ViewState.Loading -> onLoading(true)
                is ViewState.Error -> onError(event.error)
                is ViewState.Success -> onSuccess(event.value)
            }
        }

        setupListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupListeners() = with(binding) {
        refreshLayout.setOnRefreshListener {
            viewModel.getNewActivity()
            refreshLayout.isRefreshing = false
        }

        buttonStart.setOnClickListener {
            setupStartedViews(true)
            viewModel.startActivity(STATUS_STARTED)
        }

        buttonNext.setOnClickListener {
            viewModel.getNewActivity()
        }

        buttonLave.setOnClickListener {
            viewModel.updateActivity(STATUS_ABORTED)
            viewModel.getNewActivity()
        }

        buttonFinish.setOnClickListener {
            viewModel.updateActivity(STATUS_FINISHED)
            viewModel.getNewActivity()
        }

        spinnerType.run {
            setAdapter(object :
                ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, ACTIVITY_TYPES) {
                override fun getFilter(): Filter {
                    return object : Filter() {
                        override fun performFiltering(constraint: CharSequence?) = null
                        override fun publishResults(constraint: CharSequence?, results: FilterResults?) = Unit
                    }
                }
            })
            addTextChangedListener {
                val typeChanged = viewModel.typeSelected != it.toString()
                viewModel.typeSelected = it.toString()
                if (typeChanged) viewModel.getNewActivity()
            }
            setText(viewModel.typeSelected, false)
        }

        setupStartedViews(viewModel.statedActivity != null)
    }

    private fun setupStartedViews(started: Boolean) = with(binding) {
        startContainer.isVisible(!started)
        endContainer.isVisible(started)
    }

    private fun onLoading(loading: Boolean) = with(binding) {
        shimmerActivity.isVisible(loading)

        if (loading) contentContainer.hide()
    }

    private fun onError(error: Throwable) = with(binding) {
        activityText.text = error.message
    }

    private fun onSuccess(response: ActivityResponse) = with(binding) {
        activityText.text = response.activity

        contentContainer.show()

        setupStartedViews(viewModel.statedActivity != null)
    }
}