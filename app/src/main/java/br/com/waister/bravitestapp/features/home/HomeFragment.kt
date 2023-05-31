package br.com.waister.bravitestapp.features.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.waister.bravitestapp.databinding.FragmentHomeBinding
import br.com.waister.bravitestapp.models.ActivityResponse
import br.com.waister.bravitestapp.utils.ViewState
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onLoading(loading: Boolean) {
        if (loading) binding.textHome.text = "Carregando..."
    }

    private fun onError(error: Throwable) {
        binding.textHome.text = error.message
    }

    private fun onSuccess(response: ActivityResponse) {
        binding.textHome.text = response.toString()
    }
}