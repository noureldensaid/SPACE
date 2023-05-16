package com.spc.space.ui.main.profile

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.spc.space.R
import com.spc.space.databinding.FragmentReportProblemBinding
import com.spc.space.models.reportProblem.ReportProblemRequest
import com.spc.space.ui.main.shared_viewmodels.DataStoreViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReportProblem : Fragment(R.layout.fragment_report_problem) {

    private val reportProblemViewModel: ReportProblemViewModel by viewModels()
    private val dataStoreViewModel: DataStoreViewModel by viewModels()

    private var _binding: FragmentReportProblemBinding? = null

    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentReportProblemBinding.bind(view)


        //val workSpaceItem = args.data
        val workspaceId = requireArguments().getString("workspaceId")
        val token = dataStoreViewModel.token.value.toString()
        binding.backIc.setOnClickListener {
            findNavController().navigateUp()
        }




        binding.btnSubmitProblem.setOnClickListener {
            val msg = binding.enterProblem.editText?.text.toString().trim()
            if (msg.isNotEmpty() && workspaceId != null) {
                val reportProblemRequest = ReportProblemRequest(report = msg)
                Log.e("report message", msg)
                Log.e("ws ID", workspaceId.toString())
                reportProblemViewModel.createReportProblem(token, workspaceId, reportProblemRequest)
            }
            // Toast.makeText(activity, "Submit successfully", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}