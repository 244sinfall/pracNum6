import java.util.Arrays;

/**
 * Двусвязный кольцевой список
 * @param <T> без ограничений
 */
public class RingedLinkedList<T> {
    /**
     * Массив-основа списка
     */
    private T[] underlyingArray;
    /**
     * Текущее значение указателя. -1 при пустом списке. 1 при указателе между 1 и 2 и.т.д.
     */
    private int pointer = -1;
    /**
     * Количество нод в списке. -1 при пустом списке
     */
    private int lastNodeIndex = -1;
    /**
     * Базовое число элементов массива, под которое выделяется память при создании списка. (если в конструктор не передано больше)
     */
    private static final int baseArrayValue = 10;
    /**
     * Коэффициент выделения новой памяти для элементов, когда список переполняет массив (в случае с 2, если было 10 элементов, станет 20, потом 40 и.т.д)
     */
    private static final int extendingArrayMultiplier = 2;
    /**
     * Конструктор
     * @param startElements - начальные элементы списка по очереди. Указатель при этом будет ссылаться на первый элемент
     */
    @SafeVarargs
    public RingedLinkedList(T... startElements) {
        if(startElements.length > 0) {
            underlyingArray = (T[]) new Object[startElements.length];
            System.arraycopy(startElements, 0, underlyingArray, 0, startElements.length);
            pointer = 0;
            lastNodeIndex = startElements.length - 1;
        }
    }

    /**
     * Перемещает указатель на один элемент вправо.
     * Если указатель находится на последней ноде, он перемещается на нулевую
     * @return true - указатель перемещен, false - указатель не перемещен (список пуст или в нем один элемент)
     */
    public boolean shiftNext() {
        if(pointer != -1 && lastNodeIndex > 0) {
            if(pointer == lastNodeIndex) {
                pointer = 0;
            } else {
                pointer++;
            }
            return true;
        }
        return false;
    }

    /**
     * Перемещает указатель на один элемент влево.
     * Если указатель находится на первой ноде, он перемещается на последнюю
     * @return true - указатель перемещен, false - указатель не перемещен (список пуст или в нем один элемент)
     */
    public boolean shiftPrev() {
        if(pointer != -1 && lastNodeIndex != 0) {
            if(pointer == 0) {
                pointer = lastNodeIndex;
            } else {
                pointer--;
            }
            return true;
        }
        return false;
    }

//    /**
//     * Получает значение, на которое указывает указатель во время вызова
//     * @return значение типа T или null если список пуст
//     */
//    public T getPointerValue() {
//        if(pointer != -1) {
//            return underlyingArray[pointer];
//        }
//        return null;
//    }

    /**
     * Перемещает указатель на первую ноду
     * @return true - указатель перемещен. false - указатель не перемещен (список пуст)
     */
    public boolean moveStart() {
        if(pointer != -1) {
            pointer = 0;
            return true;
        }
        return false;
    }
    /**
     * Перемещает указатель на последнюю ноду
     * @return true - указатель перемещен. false - указатель не перемещен (список пуст)
     */
    public boolean moveTail() {
        if(pointer != -1) {
            pointer = lastNodeIndex;
            return true;
        }
        return false;
    }

    /**
     * @return Размер списка без учета выделенных индексов под новые элементы
     */
    public int size() {
        return lastNodeIndex + 1;
    }

    /**
     * @return true - список пуст, false - список не пуст
     */
    public boolean isEmpty() {
        return underlyingArray == null || size() == 0;
    }

    /**
     * Служебный метод
     * Выполняет инициализацию списка при добавлении первого элемента (если вызван пустой конструктор)
     * @param item Элемент для добавления
     */
    private void instantiate(T item) {
        underlyingArray = (T[]) new Object[baseArrayValue];
        underlyingArray[0] = item;
        pointer = 0;
        lastNodeIndex = 0;
    }

    /**
     * Служебный метод
     * Выполняет расширение массива при переполнении
     */
    private void extendUnderlyingArray() {
        T[] newArray = (T[]) new Object[size() * extendingArrayMultiplier];
        System.arraycopy(underlyingArray, 0, newArray, 0, size());
        underlyingArray = newArray;
    }

    /**
     * Меняет местами следующий и предыдущий элементы указателя
     * @return true - перестановка выполнена, false - перестановка не выполнена (указатель в начале/конце списка, в списке менее 3 элементов)
     */
    public boolean replacePrevAndNext() {
//        if(pointer < 1 || pointer == lastNodeIndex || lastNodeIndex < 2) return false;
        if(pointer < 0 || lastNodeIndex < 1) return false;
        if(underlyingArray[getPrevPointerValue()] == null || underlyingArray[getNextPointerValue()] == null) return false;
        T temp = underlyingArray[getPrevPointerValue()];
        underlyingArray[getPrevPointerValue()] = underlyingArray[getNextPointerValue()];
        underlyingArray[getNextPointerValue()] = temp;
        return true;
    }

    /**
     * Удаляет элемент перед указателем
     * @return true - удаление выполнено, false - удаление не выполнено (указатель в начале списка, список пуст)
     */
    public boolean removeBefore() {
        if(size() == 0) return false;
        if(size() == 1) {
            underlyingArray = null;
            pointer = -1;
            lastNodeIndex = -1;
            return true;
        }
        pointer = getPrevPointerValueIn(2);
        removeAfter();
        return true;
    }

    /**
     * Удаляет элемент после указателя
     * @return true - удаление выполнено, false - удаление не выполнено (указатель в конце списка, список пуст)
     */
    public boolean removeAfter() {
        int oldPointer = pointer;
        if(size() == 0) return false;
        if(size() == 1) {
            underlyingArray = null;
            pointer = -1;
            lastNodeIndex = -1;
            return true;
        }
        do {
            try {
                T temp = underlyingArray[getNextPointerValueIn(2)];
                underlyingArray[getNextPointerValue()] = temp;
                pointer++;
            } catch (Exception e) {
                break;
            }

        }
        while (pointer != lastNodeIndex);
        pointer = oldPointer;
        underlyingArray[lastNodeIndex] = null;
        lastNodeIndex--;
        return true;
    }

    /**
     * Добавляет элемент в список после указателя
     * @param item элемент для добавления
     */
    public void addAfter(T item) {
        int oldPointer = pointer;
        if(pointer == -1) {
            instantiate(item);
            return;
        }
        if(lastNodeIndex + 1 >= underlyingArray.length) {
            extendUnderlyingArray();
            if(this.size() == 1) {
                underlyingArray[1] = item;
                lastNodeIndex++;
                return;
            }
        }
        T temp = underlyingArray[getNextPointerValue()];
        underlyingArray[getNextPointerValue()] = item;
        pointer = getNextPointerValue();
        while(pointer != lastNodeIndex) {
            T temp2 = temp;
            temp = underlyingArray[getNextPointerValue()];
            underlyingArray[getNextPointerValue()] = temp2;
            pointer = getNextPointerValue();
        }
        lastNodeIndex++;
        underlyingArray[lastNodeIndex] = temp;
        pointer = oldPointer;
    }

    /**
     * Перенос указателя на определенное количество элементов. Учитывает кольцо
     * @param delta на сколько элементов сдвинуть вперед
     * @return индекс после сдвига
     */
    private int getNextPointerValueIn(int delta) {
        if(pointer == -1) return -1;
        if(this.size() == 1) return 0;
        if(pointer + delta <= lastNodeIndex) return pointer + delta;
        return lastNodeIndex - pointer + (delta-1);
    }

    /**
     * Перенос указателя на определенное количество элементов. Учитывает кольцо
     * @param delta на сколько элементов сдвинуть назад
     * @return индекс после сдвига
     */
    private int getPrevPointerValueIn(int delta) {
        if(pointer == -1) return -1;
        if(this.size() == 1) return 0;
        if(pointer - delta + 1 >= 0) return pointer - delta + 1;
        while(pointer - delta + 1 <= lastNodeIndex) {
            delta -= lastNodeIndex + 1;
        }
        return delta;
    }
    private int getPrevPointerValue() {
        return pointer;
    }
    private int getNextPointerValue() {
        if(pointer == -1) return -1;
        if(this.size() == 1) return 0;
        if(pointer + 1 <= lastNodeIndex) return pointer + 1;
        return lastNodeIndex - pointer;
    }
    public T[] getCurrentPointerValues() {
        T prev = underlyingArray[getPrevPointerValue()];
        T next = underlyingArray[getNextPointerValue()];
        if (next == prev) return (T[]) new Object[]{prev};
        return (T[]) new Object[]{prev, next};
    }
    /**
     * Добавляет элемент в список перед указателем
     * @param item элемент для добавления
     */
    public void addBefore(T item) {
        addAfter(item);
        shiftNext();
    }

    /**
     * Возвращает массив из списка
     * @return массив T
     */
    public T[] toArray() {
        T[] noNullArray = (T[]) new Object[size()];
        System.arraycopy(underlyingArray, 0, noNullArray, 0, size());
        return noNullArray;
    }

    @Override
    public String toString() {
        return Arrays.toString(this.toArray());
    }
}
