package laba;

import javax.swing.table.AbstractTableModel;

public class GornerTableModel extends AbstractTableModel {
    private Double[] coefficients;
    private Double from;
    private Double to;
    private Double step;
    //Значение многочлена в точке
    private Double res = 0.0;

    public GornerTableModel(Double from, Double to, Double step, Double[] coefficients) {
        this.from = from;
        this.to = to;
        this.step = step;
        this.coefficients = coefficients;
    }

    public Double getFrom() {
        return from;
    }

    public Double getTo() {
        return to;
    }

    public Double getStep() {
        return step;
    }

    public int getColumnCount() {
// В данной модели два столбца
        return 3;
    }

    public int getRowCount() {
// Вычислить количество точек между началом и концом отрезка
// исходя из шага табулирования
        return new Double(Math.ceil((to - from) / step)).intValue() + 1;
    }

    public Object getValueAt(int row, int col) {
        // Вычислить значение X как НАЧАЛО_ОТРЕЗКА + ШАГ*НОМЕР_СТРОКИ
        double x = from + step * row;
        if (col == 0) {
// Если запрашивается значение 1-го столбца, то это X
            return x;
        } else if (col == 1) {
// Если запрашивается значение 2-го столбца, то это значение многочлена
// Вычисление значения в точке по схеме Горнера
            Double result = 0.0;
            for (int i = 0; i < coefficients.length; i++) {
                result += coefficients[i] * Math.pow(x, coefficients.length - i - 1);
            }
//Копируем результат для вычислений в третьей колонке
            res = result;
            return result;
        } else {
//Если запрашивается значение 3-го столбца:
//Возвращает true, если дробная часть значения многочлена в точке является записью квадрата целого числа
            int b = (int) Math.round(res);
            double result;
            boolean flag = false;
            if (b > res) {
                result = b - res;
            } else {
                result = res - b;
            }
            Math.abs(result);
            if (result >= 0 && result < 0.1) {
                flag = true;
            }
            return flag;
        }
    }

    public String getColumnName(int col) {
        switch (col) {
            case 0:
// Название 1-го столбца
                return "Значение X";
            case 1:
// Название 2-го столбца
                return "Значение многочлена";
            case 2:
//Название 3-го столбца
                return "Близко к целому";
            default:
                throw new IllegalStateException("Unexpected value: " + col);
        }
    }

    public Class<?> getColumnClass(int col) {

        switch (col) {
//В 3-ем столбце находятся значения типа Boolean
            case 2:
                return Boolean.class;
            default:
//В 1-ом и во 2-ом столбце находятся значения типа Double
                return Double.class;
        }
    }

}
