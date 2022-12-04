package com.example.orderplatform.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.orderplatform.R
import com.example.orderplatform.database.InfoDao
import com.example.orderplatform.database.MDataBaseHelper
import com.example.orderplatform.entity.InfoEntity

class InfoFragment : Fragment(), OnClickListener {
    private var mHelper: MDataBaseHelper? = null

    private var infoDao: InfoDao? = null

    private var addressEditText: EditText? = null

    private var radioGroup: RadioGroup? = null

    private var mView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mHelper = context?.let { MDataBaseHelper(it) }
        infoDao = InfoDao(mHelper!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.fragment_info, container, false)
        initData()
        return mView
    }

    private fun initData() {
        mView?.let {
            addressEditText = it.findViewById(R.id.info_address)
            radioGroup = it.findViewById(R.id.info_group)
            it.findViewById<Button>(R.id.info_button).setOnClickListener(this)
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.info_button -> {
                val address = addressEditText!!.text.toString()
                if (address.isEmpty()) {
                    Toast.makeText(context, "请先填写默认地址", Toast.LENGTH_SHORT).show()
                } else {
                    val infoList = infoDao!!.findDefault(1)
                    if (infoList.size > 1) {
                        for (info in infoList) {
                            infoDao!!.updatePayById(info.id, 0)
                        }
                    }
                    infoDao!!.insert(
                        InfoEntity(
                            address,
                            change(radioGroup!!.checkedRadioButtonId),
                            1
                        )
                    )
                    Toast.makeText(context, "修改成功", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun change(id: Int?): Int {
        when (id) {
            R.id.info_wechat -> return 0
            R.id.info_alipay -> return 1
            R.id.info_visa -> return 2
        }
        return 0
    }
}