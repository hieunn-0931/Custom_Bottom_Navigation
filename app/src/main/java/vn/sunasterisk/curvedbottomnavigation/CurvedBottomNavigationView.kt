package vn.sunasterisk.curvedbottomnavigation

import android.content.Context
import android.graphics.*
import com.google.android.material.bottomnavigation.BottomNavigationView

class CurvedBottomNavigationView(context: Context) : BottomNavigationView(context) {
    private val path = Path()
    private val paint = Paint()

    private var firstCurveStartPoint = Point()
    private var firstCurveEndPoint = Point()

    private val firstCurveControlPoint1 = Point()
    private val firstCurveControlPoint2 = Point()
    private var secondCurveStartPoint = Point()
    private val secondCurveEndPoint = Point()
    private val secondCurveControlPoint1 = Point()
    private val secondCurveControlPoint2 = Point()

    private var navigationBarWidth: Int = 0
    private var navigationBarHeight: Int = 0

    init {
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.color = Color.WHITE
        setBackgroundColor(Color.TRANSPARENT)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        navigationBarWidth = width
        navigationBarHeight = height
        firstCurveStartPoint.set(
            (navigationBarWidth / 2) - (CURVE_CIRCLE_RADIUS * 2) -
                    (CURVE_CIRCLE_RADIUS / 3), 0
        )
        firstCurveEndPoint.set(
            navigationBarWidth / 2,
            CURVE_CIRCLE_RADIUS + (CURVE_CIRCLE_RADIUS / 4)
        )
        secondCurveStartPoint = firstCurveEndPoint
        secondCurveEndPoint.set(
            (navigationBarWidth / 2) + (CURVE_CIRCLE_RADIUS * 2) + (CURVE_CIRCLE_RADIUS / 3), 0
        )

        firstCurveControlPoint1.set(
            firstCurveStartPoint.x + CURVE_CIRCLE_RADIUS + (CURVE_CIRCLE_RADIUS / 4),
            firstCurveStartPoint.y
        )

        firstCurveControlPoint2.set(
            firstCurveEndPoint.x - (CURVE_CIRCLE_RADIUS * 2) + CURVE_CIRCLE_RADIUS,
            firstCurveEndPoint.y
        )

        secondCurveControlPoint1.set(
            secondCurveStartPoint.x + (CURVE_CIRCLE_RADIUS * 2) - CURVE_CIRCLE_RADIUS,
            secondCurveStartPoint.y
        )
        firstCurveControlPoint2.set(
            secondCurveEndPoint.x - (CURVE_CIRCLE_RADIUS + (CURVE_CIRCLE_RADIUS / 4)),
            secondCurveEndPoint.y
        )

        path.apply {
            reset()
            moveTo(0f, 0f)
            lineTo(firstCurveStartPoint.x.toFloat(), firstCurveStartPoint.y.toFloat())
            cubicTo(
                firstCurveControlPoint1.x.toFloat(), firstCurveControlPoint1.y.toFloat(),
                firstCurveControlPoint2.x.toFloat(), firstCurveControlPoint2.y.toFloat(),
                firstCurveEndPoint.x.toFloat(), firstCurveEndPoint.y.toFloat()
            )
            cubicTo(
                secondCurveControlPoint1.x.toFloat(), secondCurveControlPoint1.y.toFloat(),
                secondCurveControlPoint2.x.toFloat(), secondCurveControlPoint2.y.toFloat(),
                secondCurveEndPoint.x.toFloat(), secondCurveEndPoint.y.toFloat()
            )
            lineTo(navigationBarWidth.toFloat(), 0f)
            lineTo(navigationBarWidth.toFloat(), navigationBarHeight.toFloat())
            lineTo(0f, navigationBarHeight.toFloat())
            close()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawPath(path, paint)
    }

    companion object {
        const val CURVE_CIRCLE_RADIUS = 128
    }
}
