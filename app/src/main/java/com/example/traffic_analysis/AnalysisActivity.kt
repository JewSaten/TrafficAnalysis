package com.example.traffic_analysis

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bin.david.form.data.CellInfo
import com.bin.david.form.data.format.bg.ICellBackgroundFormat
import com.blankj.utilcode.util.ToastUtils
import com.example.traffic_analysis.databinding.ActivityAnalysisBinding
import com.jew.traffic_analysis.ext.AnalysisCallback
import com.jew.traffic_analysis.ext.TAManager
import com.jew.traffic_analysis.ext.WrapperPacket

private const val EXTRA_KEY_PATH = "com.example.fpath"
class AnalysisActivity : AppCompatActivity() {
    private lateinit var binding:ActivityAnalysisBinding

    companion object{
        @JvmStatic fun start(context: Context,fpath: String?) {
            val intent = Intent(context, AnalysisActivity::class.java)
            intent.putExtra(EXTRA_KEY_PATH,fpath)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAnalysisBinding.inflate(layoutInflater)
        setContentView(binding.root)

        TAManager.scanPackets(this,intent.getStringExtra(EXTRA_KEY_PATH),object :AnalysisCallback{
            override fun onError(message: String?) {
                ToastUtils.showLong(message)
            }

            override fun onSuccess(result: MutableList<WrapperPacket>) {
                binding.table.setData(rebuildData(result))
            }

        })
        binding.table.apply {
            config.isShowXSequence = false
            config.isShowYSequence = false
            config.isShowTableTitle =false

            tableData?.apply {
                setOnRowClickListener { _, item, _, row ->
                    val capturePacket = item as CapturePacket

                    config.contentCellBackgroundFormat = object :ICellBackgroundFormat<CellInfo<*>>{
                        override fun drawBackground(
                            canvas: Canvas?,
                            rect: Rect?,
                            cellInfo: CellInfo<*>?,
                            paint: Paint?,
                        ) {
                            paint?.color = if(cellInfo?.row == row)
                                Color.parseColor("#4D3294") else Color.TRANSPARENT
                            canvas?.drawRect(rect!!,paint!!)
                        }

                        override fun getTextColor(cellInfo: CellInfo<*>?): Int =
                            if(cellInfo?.row == row) Color.WHITE else 0
                    }
                    invalidate()

                    with(supportFragmentManager.beginTransaction()) {
                        replace(
                            R.id.fragment_container,
                            AnalysisFragment.newInstance(capturePacket.detail),
                            AnalysisFragment.TAG
                        )
                        addToBackStack(null)
                        commit()
                    }
                }
            }
        }
    }

    private fun rebuildData(origins : MutableList<WrapperPacket>?): MutableList<CapturePacket>{
        val data = mutableListOf<CapturePacket>()
        origins?.forEachIndexed {index,origin->
            val capturePacket = CapturePacket(
                origin.frame.number,
                origin.frame.arrivalTime,
                "${origin.summary?.srcIp}:${origin.summary?.srcPort}",
                "${origin.summary?.destIp}:${origin.summary?.destPort}",
                origin.summary?.protocol.toString(),
                origin.frame.capLen,
                origin.summary?.info.toString(),
                origin.detail.toString()
            )
            data.add(capturePacket)
        }
        return data
    }

}