package com.manneia.examples.realize;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.manneia.examples.realize.TreeNode;

/**
 * 算法合集
 *
 * @author lkx
 */
@SuppressWarnings("unused")
public class Solution {
	/**
	 * 两数之和
	 *
	 * @param list   整型数组
	 * @param target 整数目标值
	 * @return 表示函数的返回值类型为一个整型数组，数组中包含找到的两个数在原数组list中的下标
	 */
	public int[] twoSum(int[] list, int target) {
		HashMap<Integer, Integer> map = new HashMap<>();
		for (int i = 0; i < list.length; i++) {
			if (map.containsKey(target - list[i])) {
				return new int[] { map.get(target - list[i]), i };
			}
			map.put(list[i], i);
		}
		throw new IllegalArgumentException("No two sum solution");
	}

	/**
	 * 判断一个整数是否是回文数, 回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
	 *
	 * @param x 待判断的整数
	 * @return 如果x是回文数，返回true，否则返回false
	 */
	public boolean isPalindrome(int x) {
		// 边界判断, 如果x小于0,直接返回false
		if (x < 0) {
			return false;
		}
		int div = 1; // 用于分割整数x的除数，初始为1
		// 找到合适的div，使得x除以div的商小于10
		while (x / div >= 10) {
			div *= 10;
		}
		// 使用div检查x的左右两侧数字是否相等
		while (x > 0) {
			int left = x / div; // 左侧数字
			int right = x % 10; // 右侧数字
			// 如果左右两侧数字不相等，直接返回false
			if (left != right) {
				return false;
			}
			x = (x % div) / 10; // 更新x，去掉已检查的右侧数字
			div /= 100; // 更新div，缩小两个倍数，因为左侧数字已去掉一位
		}
		return true;
	}

	// 罗马数字与对应的整数值映射
	static final HashMap<Character, Integer> ROMAN_MAP = new HashMap<>();

	static {
		// 初始化罗马数字映射
		ROMAN_MAP.put('I', 1);
		ROMAN_MAP.put('V', 5);
		ROMAN_MAP.put('X', 10);
		ROMAN_MAP.put('L', 50);
		ROMAN_MAP.put('C', 100);
		ROMAN_MAP.put('D', 500);
		ROMAN_MAP.put('M', 1000);
	}

	/**
	 * 根据输入的罗马数字字符，获取其对应的整数值。
	 *
	 * @param i 罗马数字的字符表示
	 * @return 对应的整数值。如果输入的字符不是有效的罗马数字字符，则返回0。
	 */
	private int getValue(char i) {
		switch (i) {
			case 'I':
				return 1; // 罗马数字I对应的整数值为1
			case 'V':
				return 5; // 罗马数字V对应的整数值为5
			case 'X':
				return 10; // 罗马数字X对应的整数值为10
			case 'L':
				return 50; // 罗马数字L对应的整数值为50
			case 'C':
				return 100; // 罗马数字C对应的整数值为100
			case 'D':
				return 500; // 罗马数字D对应的整数值为500
			case 'M':
				return 1000; // 罗马数字M对应的整数值为1000
			default:
				return 0; // 对于非罗马数字的字符，返回0
		}
	}

	/**
	 * 将罗马数字转换为整数。
	 *
	 * @param s 表示罗马数字的字符串
	 * @return 罗马数字代表的整数值
	 */
	public int oldRomanToInt(String s) {
		int result = 0; // 初始化结果为0
		int i = 0;
		while (i < s.length()) {
			// 获取当前罗马数字对应的整数值
			int currentValue = ROMAN_MAP.get(s.charAt(i));
			// 判断当前罗马数字是否被更大的罗马数字紧随，若是，则需要从结果中减去当前值
			if (i + 1 < s.length() && currentValue < ROMAN_MAP.get(s.charAt(i + 1))) {
				result += ROMAN_MAP.get(s.charAt(i + 1)) - currentValue;
				i += 2; // 跳过两个字符，处理下一个罗马数字
				continue;
			} else {
				// 若不是，则直接累加到结果中
				result += currentValue;
			}
			i++; // 移动到下一个罗马数字
		}
		return result; // 返回转换后的整数
	}

	/**
	 * 将罗马数字转换为整数。时间复杂度更低
	 *
	 * @param s 表示罗马数字的字符串
	 * @return 罗马数字代表的整数值
	 */
	public int newRomanToInt(String s) {
		int result = 0; // 初始化结果为0
		int i = 0;
		while (i < s.length()) {
			// 获取当前罗马数字对应的整数值
			int currentValue = getValue(s.charAt(i));
			// 判断当前罗马数字是否被更大的罗马数字紧随，若是，则需要从结果中减去当前值
			if (i + 1 < s.length() && currentValue < getValue(s.charAt(i + 1))) {
				result += getValue(s.charAt(i + 1)) - currentValue;
				i += 2; // 跳过两个字符，处理下一个罗马数字
				continue;
			} else {
				// 若不是，则直接累加到结果中
				result += currentValue;
			}
			i++; // 移动到下一个罗马数字
		}
		return result; // 返回转换后的整数
	}

	/**
	 * 获取字符串数组中的最长公共前缀。
	 *
	 * @param strs 字符串数组，包含需要比较的多个字符串
	 * @return 最长公共前缀，如果不存在公共前缀则返回空字符串
	 */
	public String longestCommonPrefix(String[] strs) {
		// 检查输入字符串数组是否为空，若为空则直接返回空字符串
		if (strs == null || strs.length == 0) {
			return "";
		}
		// 以数组第一个字符串作为初始的最长公共前缀
		String prefix = strs[0];
		// 遍历数组中的剩余字符串，逐个比较以更新最长公共前缀
		for (int i = 1; i < strs.length; i++) {
			// 对当前字符串和当前最长公共前缀进行比较，更新最长公共前缀
			prefix = getCommonPrefix(prefix, strs[i]);
			// 若最长公共前缀为空字符串，则直接返回
			if (prefix.isEmpty()) {
				return "";
			}
		}
		return prefix;
	}

	/**
	 * 获取两个字符串的最长公共前缀。
	 *
	 * @param str1 第一个字符串
	 * @param str2 第二个字符串
	 * @return 最长公共前缀
	 */
	private String getCommonPrefix(String str1, String str2) {
		int index = 0;
		// 遍历两个字符串，找到其公共前缀的长度
		while (index < str1.length() && index < str2.length() && str1.charAt(index) == str2.charAt(index)) {
			index++;
		}
		// 返回两个字符串的公共前缀部分
		return str1.substring(0, index);
	}

	/**
	 * 定义了一个静态常量Map，用于存储括号对。
	 * 这个Map将每个左括号映射到其相应的右括号。
	 */
	static final Map<Character, Character> BRACKET_PAIRS = new HashMap<>();

	static {
		// 初始化括号对映射
		BRACKET_PAIRS.put('(', ')');
		BRACKET_PAIRS.put('[', ']');
		BRACKET_PAIRS.put('{', '}');
	}

	/**
	 * 判断给定的字符串中的括号是否匹配。
	 * 具体而言，如果字符串中的每个括号都能找到其匹配的括号，则认为是匹配的。
	 * 支持的括号类型包括：圆括号()、方括号[]和花括号{}。
	 *
	 * @param s 待检查的字符串，仅包含括号字符。
	 * @return 如果字符串中的括号完全匹配，则返回true；否则返回false。
	 */
	public boolean isValid(String s) {
		if (s == null) {
			return false;
		}
		char[] leftList = new char[s.toCharArray().length];
		int leftMaxNum = -1; // 记录左括号的栈顶位置

		// 遍历字符串中的每个字符
		for (char c : s.toCharArray()) {
			if (c == '(' || c == '[' || c == '{') { // 遇到左括号，入栈
				leftMaxNum++;
				leftList[leftMaxNum] = c;
			} else { // 遇到右括号，尝试匹配
				if (check(leftList, leftMaxNum, c)) { // 成功匹配，左括号出栈
					leftMaxNum--;
				} else { // 无法匹配，返回false
					return false;
				}
			}
		}
		// 如果栈中没有未匹配的左括号，则返回true
		return leftMaxNum < 0;
	}

	/**
	 * 根据栈中的左括号，检查当前右括号是否匹配。
	 *
	 * @param leftList   存放左括号的数组
	 * @param leftMaxNum 左括号栈的栈顶位置
	 * @param c          当前右括号字符
	 * @return 如果右括号和栈顶的左括号匹配，返回true；否则返回false。
	 */
	private boolean check(char[] leftList, int leftMaxNum, char c) {
		if (leftMaxNum < 0) { // 栈为空，无法匹配
			return false;
		}
		switch (c) {
			case ')':
				return leftList[leftMaxNum] == '(';
			case ']':
				return leftList[leftMaxNum] == '[';
			case '}':
				return leftList[leftMaxNum] == '{';
			default:
				return false; // 非括号字符，不处理
		}
	}

	public static class ListNode {
		int val;
		ListNode next;

		ListNode() {
		}

		ListNode(int val) {
			this.val = val;
		}

		ListNode(int val, ListNode next) {
			this.val = val;
			this.next = next;
		}
	}

	/**
	 * 合并两个有序链表。
	 *
	 * @param list1 第一个有序链表的头节点。
	 * @param list2 第二个有序链表的头节点。
	 * @return 合并后有序链表的头节点。
	 */
	public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
		// 创建一个虚拟头节点和一个指向当前节点的指针，用于构建合并后的链表
		ListNode dum = new ListNode(0), cur = dum;

		// 遍历两个链表，将较小的节点添加到合并后的链表中
		while (list1 != null || list2 != null) {
			if (list1 == null) {
				cur.next = list2;
				list2 = list2.next;
			} else if (list2 == null) {
				cur.next = list1;
				list1 = list1.next;
			} else {
				if (list1.val < list2.val) {
					cur.next = list1;
					list1 = list1.next;
				} else {
					cur.next = list2;
					list2 = list2.next;
				}
			}

			cur = cur.next; // 移动到下一个位置以添加下一个节点
		}

		return dum.next; // 返回合并后链表的头节点
	}

	/**
	 * 从数组中移除重复的元素，返回移除重复元素后数组的长度。
	 * 注意：此方法会改变原数组的内容。
	 *
	 * @param nums 待处理的整型数组，可能为null或空数组。
	 * @return 返回处理后的数组长度，即移除重复元素后的长度。
	 */
	public int removeDuplicates(int[] nums) {
		// 处理输入为空的情况
		if (nums == null || nums.length == 0) {
			return 0;
		}
		int p = 0, q = 1; // p指向当前非重复元素的最后一个位置，q指向正在检查的元素
		while (q < nums.length) {
			// 如果发现新的元素与前一个非重复元素不相同，则将其加入到非重复序列中
			if (nums[p] != nums[q]) {
				// 当前元素与前一个非重复元素不同，且距离大于1，需要移动元素以消除间隔
				if (q - p > 1) {
					nums[p + 1] = nums[q];
				}
				p++; // 更新非重复序列的结束位置
			}
			q++; // 检查下一个元素
		}
		return p + 1; // 返回非重复序列的长度
	}

	/**
	 * 从数组中移除指定元素
	 *
	 * @param nums 原始数组
	 * @param val  需要移除的元素值
	 * @return 移除元素后数组的长度
	 */
	public int removeElement(int[] nums, int val) {
		int len = nums.length, left = 0;
		// 如果数组为空，直接返回0
		if (len == 0) {
			return 0;
		}
		// 遍历数组，将不等于val的元素移到数组的左侧
		for (int right = 0; right < len; right++) {
			if (nums[right] != val) {
				nums[left] = nums[right]; // 将不等于val的元素放到数组左侧
				left++; // 左指针右移
			}
		}
		// 返回移除元素后的数组长度
		return left;
	}

	/**
	 * 在haystack字符串中搜索needle字符串的第一个出现位置。
	 *
	 * @param haystack 搜索目标字符串，相当于要搜索的" haystack"。
	 * @param needle   需要搜索的字符串，相当于要找到的"needle"。
	 * @return 返回needle在haystack中的起始位置，如果未找到返回-1。
	 */
	public int strStr(String haystack, String needle) {
		int m = haystack.length(); // haystack字符串的长度
		int n = needle.length(); // needle字符串的长度
		char[] stringMatchCharArray = haystack.toCharArray(); // 将haystack转换为字符数组，便于比较
		char[] patternMatchCharArray = needle.toCharArray(); // 将needle转换为字符数组，便于比较

		// 遍历haystack字符串，每次步进i，检查是否匹配needle
		for (int i = 0; i <= m - n; i++) {
			int a = i;
			int b = 0;
			// 比较haystack和needle的字符，看是否匹配
			while (b < n && stringMatchCharArray[a] == patternMatchCharArray[b]) {
				a++;
				b++;
			}
			// 如果needle的所有字符都匹配，则返回当前位置i
			if (b == n) {
				return i;
			}
		}
		// 如果遍历结束仍未找到匹配的needle，则返回-1
		return -1;
	}

	/**
	 * 在给定的有序整数数组中查找目标值，如果找到则返回其索引，如果未找到则返回插入位置的索引。
	 *
	 * @param nums   有序整数数组，其中的元素范围从 nums[0] 到 nums[nums.length-1]。
	 * @param target 需要查找或插入的目标值。
	 * @return 目标值在数组中的索引（如果存在），否则返回插入位置的索引。
	 */
	public int searchInsert(int[] nums, int target) {
		// 初始化左右指针，左指针指向数组开始，右指针指向数组结束
		int left = 0, right = nums.length - 1;
		while (left <= right) {
			// 计算中间位置
			int mid = (left + right) / 2;
			if (nums[mid] == target) {
				// 如果中间位置的值等于目标值，直接返回中间位置
				return mid;
			} else if (nums[mid] < target) {
				// 如果中间位置的值小于目标值，将左指针移到中间位置的右边
				left = mid + 1;
			} else {
				// 如果中间位置的值大于目标值，将右指针移到中间位置的左边
				right = mid - 1;
			}
		}
		// 如果循环结束仍未找到目标值，返回左指针位置，即为插入位置
		return left;
	}

	/**
	 * 计算字符串中最后一个单词的长度。
	 *
	 * @param s 输入的字符串，可以包含空格，空格分隔单词。
	 * @return 最后一个单词的长度。如果字符串为空或者只有一个空格，则返回0。
	 */
	public int lengthOfLastWord(String s) {
		// 定位到字符串末尾
		int end = s.length() - 1;
		// 从末尾向前找到第一个非空格字符，确定最后一个单词的起始位置
		while (end > 0 && s.charAt(end) == ' ') {
			end--;
		}
		// 如果字符串全是空格，则没有单词，返回0
		if (end < 0) {
			return 0;
		}
		// 确定最后一个单词的结束位置
		int start = end;
		// 从最后一个非空格字符向前找到第一个空格字符
		while (start >= 0 && s.charAt(start) != ' ') {
			start--;
		}
		// 返回最后一个单词的长度
		return end - start;
	}

	/**
	 * 将给定的整数数组表示的数值加一。
	 * 数组中的每个元素代表数值的一位，例如，[1, 2, 3] 表示数字 123。
	 * 当加一操作导致某一位数溢出（即变为 10）时，该位数将被重置为 0，并向高位进位。
	 * 如果最高位溢出，则在数组前添加一个新元素 1，表示结果的更高位。
	 *
	 * @param digits 整数数组，代表一个非负整数。
	 * @return 加一后的整数数组。
	 */
	public int[] plusOne(int[] digits) {
		// 从数组的最后一位开始，逐位检查是否需要进位
		for (int i = digits.length - 1; i > 0; i--) {
			// 如果当前位是 9，则将其重置为 0，并尝试向高位进位
			if (digits[i] == 9) {
				digits[i] = 0;
			} else {
				// 如果当前位不是 9，则将其加一，并结束循环
				digits[i]++;
				return digits;
			}
		}
		// 如果最高位溢出，创建一个新数组，将最高位设置为 1，并返回
		digits = new int[digits.length + 1];
		digits[0] = 1;
		return digits;
	}

	/**
	 * 实现一个二进制加法器。
	 *
	 * @param a 二进制字符串表示的第一个加数。
	 * @param b 二进制字符串表示的第二个加数。
	 * @return 二进制字符串表示的加法结果。
	 */
	public String addBinary(String a, String b) {
		StringBuilder ans = new StringBuilder();
		int len = Math.max(a.length(), b.length()), carry = 0;
		for (int i = 0; i < len; i++) {
			carry += i < a.length() ? (a.charAt(a.length() - 1 - i) - '0') : 0;
			carry += i < b.length() ? (b.charAt(b.length() - 1 - i) - '0') : 0;
			ans.append((char) (carry % 2 + '0'));
			carry /= 2;
		}
		if (carry > 0) {
			ans.append('1');
		}
		ans.reverse();
		return ans.toString();
	}

	/**
	 * 实现一个函数，该函数接收一个整数 x，并返回其平方根的整数部分。
	 *
	 * @param x 整数。
	 * @return 平方根的整数部分。
	 * @description 使用牛顿迭代法求解浮点数 x 的平方根。
	 *
	 */
	public static int sqrt(int x) {
		// 如果x为0，直接返回0
		if (x == 0) {
			return 0;
		}
		// 初始化迭代的起点和当前估计值
		double x0 = x;
		// 迭代直到满足精度要求
		while (true) {
			// 根据牛顿迭代法更新估计值
			double xi = 0.5 * (x0 + (double) x / x0);
			// 如果当前估计值与上一次的估计值之差小于给定的精度，则停止迭代
			if (Math.abs(x0 - xi) < 1e-7) {
				break;
			}
			// 更新当前估计值
			x0 = xi;
		}
		// 返回最终结果的整数部分
		return (int) x0;
	}

	/**
	 * 返回爬楼梯的方法数。
	 * 
	 * @param n 爬楼梯的阶数。
	 * @description 实现一个函数，该函数接收一个整数 n，n为楼梯阶数, 每次 1 阶或者 2 阶，返回爬楼梯的方法数。
	 * @return 爬楼梯的方法数。
	 */
	public int climbStairs(int n) {
		if (n == 0 || n == 1) {
			return 1;
		}
		if (n == 2) {
			return 2;
		}
		int a = 1, b = 1, sum;
		for (int i = 0; i < n - 1; i++) {
			sum = a + b;
			a = b;
			b = sum;
		}
		return b;
	}

	/**
	 * 删除排序链表中的重复元素
	 *
	 * @param head 链表头结点
	 * @return 返回去重后的链表头结点
	 */
	public ListNode deleteDuplicates(ListNode head) {
		if (head == null) {
			return head;
		}
		ListNode cur = head;
		while (cur.next != null) {
			if (cur.val == cur.next.val) {
				cur.next = cur.next.next;
			} else {
				cur = cur.next;
			}
		}
		return head;
	}

	/**
	 * 合并两个有序数组。
	 * 将nums1和nums2中的元素合并到nums1中，要求合并后的nums1仍然是有序的。
	 * 假设nums1有足够的空间（长度等于m + n）来保存合并后的数组。
	 *
	 * @param nums1 第一个有序数组，合并后的元素将存储在这个数组中
	 * @param m     nums1中原有的元素个数
	 * @param nums2 第二个有序数组，需要合并到nums1中
	 * @param n     nums2中原有的元素个数
	 */
	public void merge(int[] nums1, int m, int[] nums2, int n) {
		/* 初始化两个指针，分别指向nums1和nums2的起始位置 */
		int p1 = 0, p2 = 0;
		/* 创建一个新数组sorted用于存储合并后的有序数组 */
		int[] sorted = new int[m + n];
		/* 用于遍历合并后的数组的指针 */
		int cur;
		/* 当任一数组还有元素未被合并时，继续循环 */
		while (p1 < m || p2 < n) {
			/* 如果nums1中的元素已全部合并完，取nums2中剩余的元素 */
			if (p1 == m) {
				cur = nums2[p2++];
			} else if (p2 == n) {
				/* 如果nums2中的元素已全部合并完，取nums1中剩余的元素 */
				cur = nums1[p1++];
			} else if (nums1[p1] < nums2[p2]) {
				/* 如果nums1当前元素小于nums2当前元素，取nums1的元素 */
				cur = nums1[p1++];
			} else {
				/* 否则，取nums2的元素 */
				cur = nums2[p2++];
			}
			/* 将当前最小元素放入合并后的数组sorted中 */
			sorted[p1 + p2 - 1] = cur;
		}
		/* 如果合并后的数组非空，将sorted中的元素复制回nums1 */
		if (m + n >= 0) {
			System.arraycopy(sorted, 0, nums1, 0, m + n);
		}
	}

	/**
	 * 中序遍历二叉树
	 * 
	 * @param root 根节点
	 * @return 返回中序遍历结果
	 */
	public List<Integer> inorderTraversal(TreeNode root) {
		List<Integer> res = new ArrayList<>();
		inorder(root, res);
		return res;
	}

	/**
	 * 中序遍历二叉树
	 * 
	 * @param root 根节点
	 * @param res  结果列表
	 */
	public void inorder(TreeNode root, List<Integer> res) {
		if (root == null) {
			return;
		}
		inorder(root.left, res);
		res.add(root.val);
		inorder(root.right, res);
	}

	/**
	 * 判断两棵二叉树是否相同
	 * 
	 * @param p 二叉树1
	 * @param q 二叉树2
	 * @return 两棵二叉树是否相同
	 */
	public boolean isSameTree(TreeNode p, TreeNode q) {
		if (p == null && q == null) {
			return true;
		} else if (p == null || q == null) {
			return false;
		} else if (p.val != q.val) {
			return false;
		} else {
			return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
		}
	}
}
