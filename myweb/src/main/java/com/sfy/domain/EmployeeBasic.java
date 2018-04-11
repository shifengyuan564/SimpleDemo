package com.sfy.domain;

/**
 * Created by shifengyuan.
 * Date: 2018/4/5
 * Time: 16:14
 */
public class EmployeeBasic implements Cloneable {

    private String stuffNo; // 人力资源系统编码
    private String name;    // 姓名

    // 计算输入
    private double m1;       // 一月份发放额
    private double m2;       // 二月份发放额
    private double m3;       // 三月份发放额
    private double yearAll;  // 一月年终奖
    private double oncePayTax;   // 一次性发放纳税

    // 输出
    private double taxFinal;    // 纳税总额

    private double m1BonusPay;     // 一月份奖金
    private double m1BonusTax;       // 一月份奖金纳税

    private double m1Pay;       // 一月发
    private double m1Tax;       // 一月发
    private double m2Pay;       // 二月发
    private double m2Tax;       // 二月发
    private double m3Pay;       // 三月发
    private double m3Tax;       // 三月发

    public String getStuffNo() {
        return stuffNo;
    }

    public void setStuffNo(String stuffNo) {
        this.stuffNo = stuffNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getM1() {
        return m1;
    }

    public void setM1(double m1) {
        this.m1 = m1;
    }

    public double getM2() {
        return m2;
    }

    public void setM2(double m2) {
        this.m2 = m2;
    }

    public double getM3() {
        return m3;
    }

    public void setM3(double m3) {
        this.m3 = m3;
    }

    public double getYearAll() {
        return yearAll;
    }

    public void setYearAll(double yearAll) {
        this.yearAll = yearAll;
    }

    public double getOncePayTax() {
        return oncePayTax;
    }

    public void setOncePayTax(double oncePayTax) {
        this.oncePayTax = oncePayTax;
    }

    public double getTaxFinal() {
        return taxFinal;
    }

    public void setTaxFinal(double taxFinal) {
        this.taxFinal = taxFinal;
    }

    public double getM1Pay() {
        return m1Pay;
    }

    public void setM1Pay(double m1Pay) {
        this.m1Pay = m1Pay;
    }

    public double getM2Pay() {
        return m2Pay;
    }

    public void setM2Pay(double m2Pay) {
        this.m2Pay = m2Pay;
    }

    public double getM3Pay() {
        return m3Pay;
    }

    public void setM3Pay(double m3Pay) {
        this.m3Pay = m3Pay;
    }

    public double getM1Tax() {
        return m1Tax;
    }

    public void setM1Tax(double m1Tax) {
        this.m1Tax = m1Tax;
    }

    public double getM2Tax() {
        return m2Tax;
    }

    public void setM2Tax(double m2Tax) {
        this.m2Tax = m2Tax;
    }

    public double getM3Tax() {
        return m3Tax;
    }

    public void setM3Tax(double m3Tax) {
        this.m3Tax = m3Tax;
    }

    public double getM1BonusPay() {
        return m1BonusPay;
    }

    public void setM1BonusPay(double m1BonusPay) {
        this.m1BonusPay = m1BonusPay;
    }

    public double getM1BonusTax() {
        return m1BonusTax;
    }

    public void setM1BonusTax(double m1BonusTax) {
        this.m1BonusTax = m1BonusTax;
    }

    @Override
    public Object clone() {
        EmployeeBasic eb = null;
        try {
            eb = (EmployeeBasic) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return eb;
    }

}
