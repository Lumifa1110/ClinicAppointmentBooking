package com.example.hyv_hpv_clinicbooking.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TabHost
import com.example.hyv_hpv_clinicbooking.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AppoinmentManagementFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AppoinmentManagementFragment : Fragment() {
    var tabHost : TabHost? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_appoinment_management, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tabHost = view.findViewById(R.id.tabHost)
        tabHost?.setup()
        var tabSpec : TabHost.TabSpec? = null
        tabSpec = tabHost?.newTabSpec("unapprovedTab")
        tabSpec?.setIndicator("Chưa duyệt", null)
        tabSpec?.setContent(R.id.unapproved_appoinment_tab)
        tabHost?.addTab(tabSpec)
        tabSpec = tabHost?.newTabSpec("approvedTab")
        tabSpec?.setIndicator("Đã duyệt", null)
        tabSpec?.setContent(R.id.approved_appoinment_tab)
        tabHost?.addTab(tabSpec)
        tabSpec = tabHost?.newTabSpec("historyTab")
        tabSpec?.setIndicator("Lịch sử", null)
        tabSpec?.setContent(R.id.history_appoinment_tab)
        tabHost?.addTab(tabSpec)
    }

}