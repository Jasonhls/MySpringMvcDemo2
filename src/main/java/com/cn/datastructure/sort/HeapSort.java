package com.cn.datastructure.sort;

/**
 * @description:
 * @author: helisen
 * @create: 2020-11-02 18:12
 **/
public class HeapSort {
    public void HeapAdjust(int[] array, int parent, int length) {
        //temp保存当前父结点
        int temp = array[parent];
        //先获得左孩子
        int child = 2 * parent + 1;
        while(child < length) {
            //如果有右孩子结点，并且右孩子结点的值大于左孩子结点，则选取右孩子结点
            if(child + 1 < length && array[child] < array[child + 1]) {
                //获取右孩子节点
                child++;
            }
            //到这里，child表示两个子孩子结点中的值大的那一个子孩子结点
            //如果父结点的值已经大于孩子结点的值，则直接结束
            if(temp >= array[child]) {
                break;
            }
            //如果父结点的值小于孩子结点的值，把孩子结点的值赋给父结点
            array[parent] =  array[child];

            //把两个子结点中大的结点的序列值赋给parent，同时选取大的结点的左孩子结点，继续向下筛选
            parent = child;
            child = 2 * child + 1;
        }

        //parent表示最大孩子结点的序列值，即把父结点的值赋给它
        array[parent] = temp;
    }

    public void heapSort(int[] list) {
        //循环建立初始堆
        for (int i = list.length / 2; i >= 0; i--) {
            HeapAdjust(list, i, list.length);
        }
        System.out.print("建立堆后的初始排序为：");
        printPart(list, 0, list.length - 1);

        //进行n-1次循环，完成排序
        for (int i = list.length - 1; i > 0; i--) {
            //最后一个元素和第一个元素进行交换
            int temp = list[i];
            list[i] = list[0];
            list[0] = temp;

            //每次交换后，都需要进行一次筛选，筛选R[0]结点，得到 i-1 个结点的堆
            HeapAdjust(list, 0, i);
            System.out.format("第 %d 躺：\t", list.length - i);
            printPart(list, 0, list.length - 1);
        }


    }

    public void printPart(int[] list, int begin, int end) {
        for (int i = 0; i < begin; i++) {
            System.out.print("\t");
        }
        for (int i = begin; i <= end; i++) {
            System.out.print(list[i] + "\t");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[] array = {1, 3, 4, 5, 2, 6, 9, 7, 8, 0};
        HeapSort heap = new HeapSort();
        System.out.print("排序前：\t");
        heap.printPart(array, 0, array.length - 1);
        heap.heapSort(array);
        System.out.print("排序后：\t");
        heap.printPart(array, 0, array.length - 1);
    }

}
