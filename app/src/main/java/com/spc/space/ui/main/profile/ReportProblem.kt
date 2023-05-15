package com.spc.space.ui.main.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.spc.space.R
 import com.spc.space.databinding.FragmentReportProblemBinding
import com.spc.space.models.createBooking.CreateBookingRequest
import com.spc.space.models.reportProblem.ReportProblemRequest
import com.spc.space.models.workspace.WorkSpaceItem
import com.spc.space.ui.DataStoreViewModel
import com.spc.space.ui.main.explore.workspaceDetails.WorkspaceDetailsFragmentArgs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReportProblem: Fragment(R.layout.fragment_report_problem) {

    private val reportProblemViewModel :ReportProblemViewModel by viewModels()
    private val dataStoreViewModel:DataStoreViewModel by viewModels()

    private var _binding: FragmentReportProblemBinding? = null

    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentReportProblemBinding.bind(view)


        //val workSpaceItem = args.data

        val wsData = this.arguments?.getParcelable<WorkSpaceItem>("wsData")
        val workspaceId =wsData?.id.toString()
        val token = dataStoreViewModel.token.value.toString()
        binding.backIc.setOnClickListener {
            findNavController().navigateUp()
        }


      // val wsData = arguments?.getParcelable<WorkSpaceItem>("wsData")
        //val workspaceId =wsData?.id.toString()
    //   val userToken = dataStoreViewModel.token.value.toString()


       //onclck listener
       val report = binding.enterProblemInfo.toString()
       binding.btnSubmitProblem.setOnClickListener{
       val reportProblemRequest =
            ReportProblemRequest(report = report)
        reportProblemViewModel.createReportProblem(token,workspaceId,reportProblemRequest)
         Toast.makeText(context,"Submit successfully",Toast.LENGTH_LONG).show()

     }
  }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}