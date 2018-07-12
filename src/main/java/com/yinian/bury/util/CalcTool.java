package com.yinian.bury.util;

import java.math.BigDecimal;

public class CalcTool {
	private BigDecimal value;

    /**
     * 提供默认精度10
     */
    private int scale = 10;

    /**
     * double类型构造函数
     * 
     * @param value
     */
    public CalcTool(double value) {
        this.value = new BigDecimal(Double.toString(value));
    }

    /**
     * String类型构造函数
     * 
     * @param value
     */
    public CalcTool(String value) {
        this.value = new BigDecimal(value);
    }

    /**
     * 取得BigDecimal的值
     * 
     * @return
     */
    public BigDecimal getValue() {
        return this.value;
    }

    /**
     * 两个double类型的数值相加
     * 
     * @param v1
     * @param v2
     * @return
     */
    public double add(double v1, double v2) {
        CalcTool a1 = new CalcTool(v1);
        CalcTool a2 = new CalcTool(v2);
        return add(a1, a2);
    }

    /**
     * 两数相除
     * 
     * @param v1
     * @param v2
     * @return
     */
    public double div(double v1, double v2) {
        CalcTool a1 = new CalcTool(v1);
        CalcTool a2 = new CalcTool(v2);
        return this.divide(a1, a2);
    }

    /**
     * 相减
     * 
     * @param v1
     * @param v2
     * @return
     */
    public double sub(double v1, double v2) {
        CalcTool a1 = new CalcTool(v1);
        CalcTool a2 = new CalcTool(v2);
        return this.subtract(a1, a2);
    }

    /**
     * 相乘
     * 
     * @param v1
     * @param v2
     * @return
     */
    public double mul(double v1, double v2) {
        CalcTool a1 = new CalcTool(v1);
        CalcTool a2 = new CalcTool(v2);
        return this.multiply(a1, a2);
    }

    /**
     * 两个CalcTool类型的数据进行相加
     * 
     * @param v1
     * @param v2
     * @return
     */
    public double add(CalcTool v1, CalcTool v2) {
        return v1.getValue().add(v2.getValue()).doubleValue();
    }

    /**
     * 两个CalcTool类型变量相除
     * 
     * @param v1
     * @param v2
     * @return
     */
    public double divide(CalcTool v1, CalcTool v2) {
        if (scale < 0) {
            throw new IllegalArgumentException("精度指定错误,请指定一个>=0的精度");
        }
        return v1.getValue().divide(v2.getValue(), scale,
                BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 两数相乘
     * 
     * @param v1
     * @param v2
     * @return
     */
    public double multiply(CalcTool v1, CalcTool v2) {
        return v1.getValue().multiply(v2.getValue()).doubleValue();
    }

    /**
     * 两数相减
     * 
     * @param v1
     * @param v2
     * @return
     */
    public double subtract(CalcTool v1, CalcTool v2) {
        return v1.getValue().subtract(v2.getValue()).doubleValue();
    }

    /**
     * 返回value的浮点数值
     * 
     * @return
     */
    public double doubleValue() {
        return this.getValue().doubleValue();
    }
    /**
     * 设置精度
     * @param scale
     */
    public void setScale(int scale) {
        this.scale = scale;
    }
    
    public static void main(String[] args) {
		CalcTool tool = new CalcTool(1);
		tool.setScale(2);
		System.out.println(tool.div(7L, 30L));
	}
}
