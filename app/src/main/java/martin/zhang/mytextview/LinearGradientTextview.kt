package martin.zhang.mytextview

import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import androidx.annotation.Nullable


/**
 *
 * created by apple on 2017/5/10.
 *
 */
open class LinearGradientTextview : androidx.appcompat.widget.AppCompatTextView {

    constructor(context: Context) : super(context) {}
    constructor(context: Context, @Nullable attrs: AttributeSet?) : super(context, attrs) {}

    private lateinit var mPaint: TextPaint
    private lateinit var mLinearGradient: LinearGradient
    private lateinit var mMatrix: Matrix

    private var mTranslate = 0f
    private var deltax = 20f
    private var mGradientSize = 0F

    /**
     *
     * 优先于onDraw执行，在这里得到系统绘制Textview的画笔，然后给这个画笔设置shader
     *
     * 这样下面在执行onDraw的时候，使用的就是带有shader效果的画笔了
     */
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        // ★拿到系统textview的画笔
        mPaint = paint
        // 得到文本
        val text: String = text.toString()
        // 用画笔测量文本的长度
        val textWidth: Float = mPaint.measureText(text)
        // 渐变区域的宽度
        mGradientSize = 100F
        // 从左边-gradientsize开始，即左边距离文字gradientsize开始渐变
        // 一个渐变单位是：从第一个字之前3字距离开始到第一个字之前位置终结
        // 然后一直让这个渐变单位从左往右移动dx位移，直到最右侧后，向左移动
        // clamp意思是：由于你的只是在渐变3个字的长度，那么剩余的空间，让边缘颜色去填充
        mLinearGradient = LinearGradient(
            -mGradientSize / 2,
            0F,
            mGradientSize / 2,
            0F,
            intArrayOf(
                Color.YELLOW, Color.RED, Color.BLUE
            ),
            null,
            Shader.TileMode.CLAMP
        )
        mPaint.shader = mLinearGradient
    }

    override fun onDraw(canvas: Canvas?) {
        // 调用super，意思是按照系统绘制textview的流程先把textview绘制完成
        super.onDraw(canvas)

        // 在系统基础上再绘制一些效果
        mTranslate += deltax
        val textWidth: Float = paint.measureText(text.toString())

        // 到了边界，从最左边重新开始
        if (mTranslate > textWidth + mGradientSize || mTranslate < 0) {
            mTranslate = 0F
        }

        // 不停地方平移，得到闪烁的效果
        mMatrix = Matrix()
        mMatrix.setTranslate(mTranslate, 0F)
        mLinearGradient.setLocalMatrix(mMatrix)

        // 延时重绘
        postInvalidateDelayed(50L)
    }
}

