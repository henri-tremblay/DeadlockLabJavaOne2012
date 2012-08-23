In our first lab, a bunch of philosophers (Thinker) are sitting around a table
at their symposium and are using two cups of wine (Krasi) to quench their
thirst.  Each of them first grabs the left and then the right cup.  If they all
grab the right cup at the same time, we will have some unhappy philosophers
caught in limbo.

1. Run the code and verify that you see a deadlock by capturing a stack trace.
   Depending on your machine, you might need a few runs to see the issue.

2. Once you have discovered the deadlock, verify that it involves the left
   and right locks.

3. Now define a global ordering for the locks.  For example, you can either let
   Krasi implement Comparable and give it a number to sort by, or you can use
   System.identityHashCode() to be able to compare the cups.  (Warning: Sadly,
   the identity hash code is not guaranteed to be unique.  Thus you have to
   plan for this.  It is easier to make Krasi comparable.)

4. Verify that the deadlock has now disappeared.

Good luck!  You have 15 minutes to solve this lab.