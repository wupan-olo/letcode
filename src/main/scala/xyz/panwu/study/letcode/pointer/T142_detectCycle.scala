package xyz.panwu.study.letcode.pointer

/**
 * 142. 环形链表 II
 * 给定一个链表，返回链表开始入环的第一个节点。 如果链表无环，则返回 null。
 *
 * 为了表示给定链表中的环，我们使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。 如果 pos 是 -1，则在该链表中没有环。
 *
 * 说明：不允许修改给定的链表。
 *
 *
 *
 * 示例 1：
 *
 * 输入：head = [3,2,0,-4], pos = 1
 * 输出：tail connects to node index 1
 * 解释：链表中有一个环，其尾部连接到第二个节点。
 *
 *
 * 示例 2：
 *
 * 输入：head = [1,2], pos = 0
 * 输出：tail connects to node index 0
 * 解释：链表中有一个环，其尾部连接到第一个节点。
 *
 *
 * 示例 3：
 *
 * 输入：head = [1], pos = -1
 * 输出：no cycle
 * 解释：链表中没有环。
 *
 * https://leetcode-cn.com/problems/linked-list-cycle-ii/
 */
object T142_detectCycle {

  /**
   * 解题思路：
   * 这类链表题目一般都是使用双指针法解决的，例如寻找距离尾部第K个节点、寻找环入口、寻找公共尾部入口等。
   * 算法流程：
   * 双指针第一次相遇： 设两指针 fast，slow 指向链表头部 head，fast 每轮走 22 步，slow 每轮走 11 步；
   *
   * 第一种结果： fast 指针走过链表末端，说明链表无环，直接返回 null；
   *
   * TIPS: 若有环，两指针一定会相遇。因为每走 11 轮，fast 与 slow 的间距 +1+1，fast 终会追上 slow；
   * 第二种结果： 当fast == slow时， 两指针在环中 第一次相遇 。下面分析此时fast 与 slow走过的 步数关系 ：
   *
   * 设链表共有 a+b 个节点，其中 链表头部到链表入口 有 a 个节点（不计链表入口节点）， 链表环 有 b
   * fast 走的步数是slow步数的 2 倍，即 f = 2s；（解析： fast 每轮走 2 步）
   * fast 比 slow多走了 nn 个环的长度，即 f = s + nb；（ 解析： 双指针都走过 a 步，然后在环内绕圈直到重合，重合时 fast 比 slow 多走 环的长度整数倍 ）；
   * 以上两式相减得：f = 2nb，s = nb，即fast和slow 指针分别走了 2n，n 个 环的周长 （注意： n 是未知数，不同链表的情况不同）。
   * 目前情况分析：
   *
   * 如果让指针从链表头部一直向前走并统计步数k，那么所有 走到链表入口节点时的步数 是：k=a+nb（先走 a 步到入口节点，之后每绕 11 圈环（ b 步）都会再次到入口节点）。
   * 而目前，slow 指针走过的步数为 nb 步。因此，我们只要想办法让 slow 再走 aa 步停下来，就可以到环的入口。
   * 但是我们不知道 a 的值，该怎么办？依然是使用双指针法。我们构建一个指针，此指针需要有以下性质：此指针和slow 一起向前走 a 步后，两者在入口节点重合。那么从哪里走到入口节点需要 aa 步？答案是链表头部head。
   * 双指针第二次相遇：
   *
   * slow指针 位置不变 ，将fast指针重新 指向链表头部节点 ；slow和fast同时每轮向前走 1 步；
   * TIPS：此时 f = 0，s = nb ；
   * 当 fast 指针走到f = a 步时，slow 指针走到步s = a+nb，此时 两指针重合，并同时指向链表环入口 。
   * 返回slow指针指向的节点。
   *
   * 作者：jyd
   * 链接：https://leetcode-cn.com/problems/linked-list-cycle-ii/solution/linked-list-cycle-ii-kuai-man-zhi-zhen-shuang-zhi-/
   * 来源：力扣（LeetCode）
   *
   * @param head
   * @param pos
   * @return
   */
  def detectCycle(head: ListNode, pos: Int): ListNode = {
    var slow = head
    var fast = head

    while (slow != null && fast.next != null && fast != slow) {
      slow = slow.next
      fast = fast.next.next
    }

    if (slow == null || fast == null) return null

    slow = head
    while (slow != fast) {
      slow = slow.next
      fast = fast.next
    }
    slow
  }
}

/**
 * Definition for singly-linked list.
 * class ListNode(var _x: Int = 0) {
 *   var next: ListNode = null
 *   var x: Int = _x
 * }
 */

class ListNode(var _x: Int = 0) {
  var next: ListNode = null
  var x: Int = _x
}
