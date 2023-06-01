package br.com.waister.bravitestapp.features.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.waister.bravitestapp.databinding.FragmentHistoryBinding
import br.com.waister.bravitestapp.utils.show
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<HistoryViewModel>()
    private var myMessagesAdapter: HistoryAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupViews() = with(binding) {
        val activities = viewModel.getActivitiesHistory()

        if (activities.isEmpty()) {
            emptyHistory.show()
        } else {
            myMessagesAdapter = HistoryAdapter()
            history.adapter = myMessagesAdapter

            myMessagesAdapter?.submitList(activities)
        }
    }
}