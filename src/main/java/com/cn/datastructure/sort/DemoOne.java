package com.cn.datastructure.sort;

import org.junit.Test;

/**
 * @description:
 * @author: helisen
 * @create: 2020-10-26 17:57
 **/
public class DemoOne {
    @Test
    public void test1() {
        a(4);
    }

    public int a(int n) {
        System.out.println("-----------" + n);
        if(n <= 1) {
            return 1;
        }else {
            return a(n - 1) + a(n - 2);
        }
    }

    @Test
    public void test2() {
        int[] a = new int[]{50, 10, 90, 30, 70, 40, 80, 60, 30};
        int[] k = quickSort(a, 0, a.length - 1);
        for (int i = 0; i < k.length; i++) {
            System.out.println(k[i] + " ");
        }
    }

    public static int[] quickSort(int[] a, int first, int last) {
        if(first < last) {
            int pivotIndex = partition(a, first, last);
            quickSort(a, first, pivotIndex - 1);
            quickSort(a, pivotIndex + 1, last);
        }
        return a;
    }

    /**
     *
     * @param a    4  7  5  8  3  1  6
     * @param left  0
     * @param right  6
     * @return
     */
    public static int partition(int[] a, int left ,int right) {
        //选取数组第一个元素为基准点
        int pivot = a[left];
        //左指针
        int leftPoint = left;
        //右指针
        int rightPoint = right;
        while(leftPoint < rightPoint) {
            //从右边开始，当leftPoint > rightPoint或a[rightPoint] <= pivot，才会退出循环
            while(leftPoint < rightPoint && a[rightPoint] > pivot) {
                rightPoint--;
            }
            //从左边开始，当leftPoint > rightPoint或a[leftPoint] >= pivot，才会退出循环
            while(leftPoint < rightPoint && a[leftPoint] <= pivot) {
                leftPoint++;
            }
            //交换low和high的位置
            if(leftPoint < rightPoint) {
                int temp = a[rightPoint];
                a[rightPoint] = a[leftPoint];
                a[leftPoint] = temp;
            }
            /**
             *然后继续循环，直到leftPoint >= rightPoint，才能退出外层循环，退出循环的时候，rightPoint会跟leftPoint相等，
             * 其值即pivot（即a[left]）在数组a中的正确位置，所以需要将a[left]与当前rightPoint位置的元素进行交换
             */
        }
        //
        a[left] = a[rightPoint];
        a[rightPoint] = pivot;
        //返回pivot在数组中的正确位置值
        return rightPoint;
    }


    public static void test3() {
        int[] a = new int[]{50, 10, 90, 30, 70, 40, 80, 60, 30};
        mergeSortHelp(a, 0, a.length);
        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]);
        }
    }

    public static void mergeSortHelp(int[] a, int start, int end) {
        if(start >= end) {
            return;
        }
        //注意每轮循环中，mid都是新的值，下面三行代码中，每轮的mid都是同一个值，
        // 不同轮的mid的值是不同的，相互不干扰的
        int mid = (start + end) / 2;
        mergeSortHelp(a, start, mid);
        mergeSortHelp(a, mid + 1, end);
        mergeHelp(a, start, mid, end);
    }

    public static void mergeHelp(int[] a, int left, int mid, int right) {
        //初始化一个排序长度的数组
        int[] temp = new int[right - left + 1];
        int i = left;
        int j = mid +1;
        int k = 0;
        //将{left-mid},{mid-right}两个数组按照顺序放到temp中，注意数组{left-mid}和数组{mid-right}各自必须都是有序的
        while(i <= mid && j <= right) {
            if(a[i] <= a[j]) {
                temp[k] = a[i];
                i++;
            }else {
                temp[k] = a[j];
                j++;
            }
            k++;
        }
        //如果上面的循环退出是因为满足 i <= mid，不满足 j <= right，也就是说{mid-right}这个数组的元素全部拷贝完了，
        //还剩下数组{left-mid}的i到mid之间的元素还没拷贝过去，所以需要把i到mid之间的元素拷贝过去
        while(i <= mid) {
            temp[k] = a[i];
            k++;
            i++;
        }
        //如果上面的循环退出是因为满足 j <= right，不满足 i <= mid，也就是说{left-mid}这个数组全部拷贝完了，
        //数组{mid-right}还有j到right之间的元素没有拷贝完，所以需要把j到right之间的元素再拷贝到temp数组后面（注意temp是从小到大的顺序）
        while(j <= right) {
            temp[k] = a[j];
            k++;
            j++;
        }
        //从temp数组中拷贝元素放到数组a中，temp数组其实位置是0，即第一个元素开始，数组a存放数据的其实位置是left，
        // 然后拷贝K个元素，这里的K就是数组temp的长度。
        System.arraycopy(temp, 0, a, left, k);
    }
}
