package com.yinian.bury.util;

import java.util.ArrayList;
import java.util.List;

public class CalcUtil {
    /**
     * 根据后缀表达式计算值
     * @param expList
     * @return
     */
    @SuppressWarnings("unused")
	private String countExp(final String expression) {
        List<String> expList = middleToBehind(expression);
        while (expList.size() > 1) {
            for (int i = 0; i < expList.size(); i++) {
                if (isOperator(expList.get(i))) {
                    expList.add(i - 2, twoOperators(expList.get(i - 2), expList.get(i - 1), expList.get(i)));
                    expList.remove(i-1);
                    expList.remove(i-1);
                    expList.remove(i-1);
                    break;
                }
            }
        }
        return expList.get(0);
    }

    /**
     * 完成两个数的四则运算
     * @param operator1
     * @param operator2
     * @param o
     * @return
     */
    private String twoOperators(String operator1, String operator2, String o){
        String result = "";
        int o1 = Integer.parseInt(operator1);
        int o2 = Integer.parseInt(operator2);
        int count = 0;
        if(o.equals("+")){
            count=o1+o2;
        }else if(o.equals("-")){
            count=o1-o2;
        }else if(o.equals("*")){
            count=o1*o2;
        }else if(o.equals("/")){
            count=o1/o2;
        }
        return result+count;
    }

    /**
     * 判定是否是运算符
     * @param s
     * @return
     */
    private boolean isOperator(String s) {
        if (s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/"))
            return true;
        return false;
    }

    /**
     * 中缀表达式转后缀表达式
     * @param expression
     * @return
     */
    private List<String> middleToBehind(String expression) {
        List<String> result = new ArrayList<String>();
        if (expression.length() == 0)
            return result;
        int begin = 0;
        char[] operator = new char[100];
        int oTop = -1;
        while (begin < expression.length()) {
            String now = "";
            if (expression.charAt(begin) <= '9' && expression.charAt(begin) >= '0') {
                while (true) {
                    if (begin < expression.length() && expression.charAt(begin) <= '9'
                            && expression.charAt(begin) >= '0') {
                        now = now + expression.charAt(begin++);
                    } else {
                        break;
                    }
                }
                result.add(now);
            } else {
                char o = expression.charAt(begin++);
                if (oTop == -1) {
                    operator[++oTop] = o;
                } else if (o == ')') {
                    // 一直弹出至'('
                    while (true) {
                        if (operator[oTop] == '(') {
                            oTop--;
                            break;
                        } else {
                            result.add("" + operator[oTop--]);
                        }
                    }
                } else if (comOperation(operator[oTop], o)) {
                    result.add("" + operator[oTop--]);
                    operator[++oTop] = o;
                } else {
                    operator[++oTop] = o;
                }
            }
        }
        while (oTop > -1) {
            result.add("" + operator[oTop--]);
        }
        return result;
    }

    /**
     * 判定操作符优先级
     * @param first
     * @param second
     * @return
     */
    private boolean comOperation(char first, char second) {
        if (first == '+') {
            if (second == '+')
                return true;
            else if (second == '-')
                return true;
            else if (second == '*')
                return false;
            else if (second == '/')
                return false;
            else if (second == '(')
                return false;
            else if (second == ')')
                return true;
        } else if (first == '-') {
            if (second == '+')
                return true;
            else if (second == '-')
                return true;
            else if (second == '*')
                return false;
            else if (second == '/')
                return false;
            else if (second == '(')
                return false;
            else if (second == ')')
                return true;
        } else if (first == '*') {
            if (second == '+')
                return true;
            else if (second == '-')
                return true;
            else if (second == '*')
                return true;
            else if (second == '/')
                return true;
            else if (second == '(')
                return false;
            else if (second == ')')
                return true;
        } else if (first == '/') {
            if (second == '+')
                return true;
            else if (second == '-')
                return true;
            else if (second == '*')
                return true;
            else if (second == '/')
                return true;
            else if (second == '(')
                return false;
            else if (second == ')')
                return true;
        } else if (first == '(') {
            if (second == '+')
                return false;
            else if (second == '-')
                return false;
            else if (second == '*')
                return false;
            else if (second == '/')
                return false;
            else if (second == '(')
                return false;
            else if (second == ')')
                return true;
        } else if (first == ')') {
            return true;
        }
        return false;
    }
}
