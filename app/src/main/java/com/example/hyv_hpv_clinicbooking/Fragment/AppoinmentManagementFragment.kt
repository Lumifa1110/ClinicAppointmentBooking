package com.example.hyv_hpv_clinicbooking.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.example.hyv_hpv_clinicbooking.Adapter.FragmentAdapter
import com.example.hyv_hpv_clinicbooking.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout

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
    private lateinit var viewPager: ViewPager
    private lateinit var tablayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_appoinment_management, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager = view.findViewById(R.id.viewPager)
        tablayout = view.findViewById(R.id.tablayout)

        val fragmentAdapter = FragmentAdapter(childFragmentManager)
        fragmentAdapter.addFragment(UnapprovedAppointmentTabView(),"Chưa duyệt")
        fragmentAdapter.addFragment(ApprovedAppointmentTabView(),"Đã duyệt")
        fragmentAdapter.addFragment(HistoryAppointmentTabView(),"Lịch sử")

        viewPager.adapter = fragmentAdapter
        tablayout.setupWithViewPager(viewPager)
    }

}



