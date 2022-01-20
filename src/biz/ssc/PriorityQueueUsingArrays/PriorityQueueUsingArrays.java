//====================================================================
//
// Application: Priority Queue Using Arrays
// Author:      Dan Ouellette
// Description:
//      This Java application demonstrates a priority queue data 
// structure implemented with an ordered array, unordered array, and a
// heap array.  It prompts for and gets from the user the number of 
// nodes to add and then remove from the three structures.  It then 
// performs the adds and removes, and times the operations.  The 
// application then prints the times for comparison.
//
//====================================================================
package biz.ssc.PriorityQueueUsingArrays;

//Import classes
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

//====================================================================
// class PriorityQueueUsingArray
//====================================================================
public class PriorityQueueUsingArrays
{
    
    //================================================================
    // Fields
    //================================================================
    
    // Declare constants
    private static final String DIVIDER = "-".repeat(80);
    private static final int RAND_UPPER_LIMIT = 1000;
    private static final int MAX_PRINT = 10;
    private static final String COLFMTS1 = "%-10s";
    private static final String COLFMTS2 = "%8s";
    private static final String COLFMTS3 = "%1s";
    private static final String COLFMTS4 = "%14s";
    private static final String COLFMTD1 = "%,1d";
    private static final String COLFMTD2 = "%4d";
    private static final String COLFMTD3 = "%14d";

    // Declare ordered variables
    private static int[] qOrdered = new int[1]; 
    private static int qOrderedCount = 0; 
    private static int qOrderedLevel = 1;
    private static int qOrderedHead = -1;
    private static int qOrderedTail = -1;

    // Declare unordered variables
    private static int[] qUnordered = new int[1]; 
    private static int qUnorderedCount = 0; 
    private static int qUnorderedLevel = 1;
    private static int qUnorderedHead = -1;
    private static int qUnorderedTail = -1;

    // Declare heap variables
    private static int[] qHeap = new int[1]; 
    private static int qHeapCount = 0; 
    private static int qHeapLevel = 1;

    //----------------------------------------------------------------
    // listNodes
    //----------------------------------------------------------------
    private static void listNodes(String type, int[] queue, int count, 
        int head, int tail, boolean print) 
    {

        // Declare variables
        int ptr;
        int i;
        
        // Test whether to print array information
        if (print) 
        {
            
            // Print array information
            System.out.printf(
                COLFMTS1 + COLFMTS2 + COLFMTS2 + COLFMTS2 + COLFMTS2,
                type, queue.length, count, head, tail);
            if (head != -1)
            {

                // Loop to show queue
                ptr = head;
                i = 0;
                while (ptr != tail)
                {
                    if (i < MAX_PRINT)
                        System.out.printf(COLFMTD2, queue[ptr]);
                    ptr = incrementPointer(ptr);
                    i = i + 1;
                }
                if (i < MAX_PRINT)
                    System.out.printf(COLFMTD2, queue[ptr]);
                
            }
            System.out.println();
            
        }
            
    }

    //----------------------------------------------------------------
    // listHeapNodes
    //----------------------------------------------------------------
    private static void listHeapNodes(boolean print)
    {
        
        // Test whether to print array information
        if (print)
        {

            // Print array information
            System.out.printf(
                COLFMTS1 + COLFMTS2 + COLFMTS2 + COLFMTS2 + COLFMTS2,
                "Heap", qHeap.length, qHeapCount, "~", "~");
            
            // Loop to print array values
            for (int i = 0; i < qHeapCount; i++)
                if (i < MAX_PRINT)
                    System.out.printf(COLFMTD2, qHeap[i]);
            System.out.println();
            
        }

    }

    //----------------------------------------------------------------
    // incrementPointer
    //----------------------------------------------------------------
    private static int incrementPointer(int ptr)
    {
        
        // Increment pointer and test if beyond array
        ptr = ptr + 1;
        if (ptr == qOrdered.length)
            ptr = 0;
        return ptr;
        
    }

    //----------------------------------------------------------------
    // decrementPointer
    //----------------------------------------------------------------
    private static int decrementPointer(int ptr)
    {
        
        // Decrement pointer and test if beyond array
        ptr = ptr - 1;
        if (ptr == -1)
            ptr = qOrdered.length - 1;
        return ptr;
        
    }

    //----------------------------------------------------------------
    // insertValueOrdered
    //----------------------------------------------------------------
    private static void insertValueOrdered(int value)
    {
        
        // Declare variables
        int prevSize;
        int addCount;
        int curr;
        int prev;
        int copyLeft;
        int copyRight;
        
        // Test if array needs resizing
        if (qOrderedCount == qOrdered.length)
        {
            
            // Resize array
            prevSize = qOrdered.length;
            addCount = (int) Math.pow(2, qOrderedLevel);
            qOrderedLevel = qOrderedLevel + 1;
            qOrdered = Arrays.copyOf(qOrdered, qOrdered.length + 
                addCount);
//            System.out.println("qOrdered array increased by " + 
//                addCount + " elements.");
            
            // Test whether to reorder so qOrderedTail after qOrderedHead
            if (qOrderedTail < qOrderedHead)
            {
                for (int i = 0; i <= qOrderedTail; i++)
                    qOrdered[prevSize + i] = qOrdered[i];
                qOrderedTail = prevSize + qOrderedTail;
            }
            
        }
        
        // Increment count
        qOrderedCount = qOrderedCount + 1;

        // Test if queue empty
        if (qOrderedHead == -1)
        {
            qOrderedHead = 0;
            qOrderedTail = 0;
            qOrdered[qOrderedTail] = value;
        }
        
        // Test if queue has one node
        else if (qOrderedHead == qOrderedTail)
            if (qOrdered[qOrderedHead] < value)
            {
                qOrderedHead = decrementPointer(qOrderedHead);
                qOrdered[qOrderedHead] = value;
            }
            else
            {
                qOrderedTail = incrementPointer(qOrderedTail);
                qOrdered[qOrderedTail] = value;
            }

        // Handle queue with more than one node
        else
        {
            if (qOrdered[qOrderedHead] < value)
            {
                qOrderedHead = decrementPointer(qOrderedHead);
                qOrdered[qOrderedHead] = value;
            }
            else
            {

                // Loop to locate spot to insert node
                prev = qOrderedHead;
                curr = incrementPointer(qOrderedHead);                    
                while (prev != qOrderedTail && 
                    qOrdered[curr] >= value)
                {
                    prev = incrementPointer(prev);
                    curr = incrementPointer(curr);
                }
                
                // Test whether to add node after tail
                if (prev == qOrderedTail)
                {
                    qOrderedTail = curr;
                    qOrdered[qOrderedTail] = value;
                }
                
                // Make space in array for new node
                else
                {
                    copyLeft = qOrderedTail;
                    qOrderedTail = incrementPointer(qOrderedTail);
                    copyRight = qOrderedTail;
                    while (copyRight != curr)
                    {
                        qOrdered[copyRight] = qOrdered[copyLeft];
                        copyLeft = decrementPointer(copyLeft);
                        copyRight = decrementPointer(copyRight);
                    }
                    qOrdered[copyRight] = value;
                }
                
            }

        }
        
    }

    //----------------------------------------------------------------
    // insertValueUnordered
    //----------------------------------------------------------------
    private static void insertValueUnordered(int value)
    {
        
        // Declare variables
        int prevSize;
        int addCount;
        
        // Test if array needs resizing
        if (qUnorderedCount == qUnordered.length)
        {
            
            // Resize array
            prevSize = qUnordered.length;
            addCount = (int) Math.pow(2, qUnorderedLevel);
            qUnorderedLevel = qUnorderedLevel + 1;
            qUnordered = 
                Arrays.copyOf(qUnordered, qUnordered.length + addCount);
//            System.out.println("qUnordered array increased by " + 
//                addCount + " elements.");
            
            // Test whether to reorder so qUnorderedTail after qUnorderedHead
            if (qUnorderedTail < qUnorderedHead)
            {
                for (int i = 0; i <= qUnorderedTail; i++)
                    qUnordered[prevSize + i] = qUnordered[i];
                qUnorderedTail = prevSize + qUnorderedTail;
            }
            
        }
        
        // Increment count
        qUnorderedCount = qUnorderedCount + 1;

        // Test if queue empty
        if (qUnorderedHead == -1)
        {
            qUnorderedHead = 0;
            qUnorderedTail = 0;
            qUnordered[qUnorderedTail] = value;
        }
        
        // Insert node in non-empty queue
        else 
        {
            qUnorderedTail = incrementPointer(qUnorderedTail);
            qUnordered[qUnorderedTail] = value;
        }
        
    }

    //----------------------------------------------------------------
    // insertValueHeap
    //----------------------------------------------------------------
    private static void insertValueHeap(int value)
    {
        
        // Declare variables
        int prevSize;
        int addCount;
        int parent;
        int child;
        int temp;
        
        // Test if array needs resizing
        if (qHeapCount == qHeap.length)
        {
            
            // Resize array
            prevSize = qHeap.length;
            addCount = (int) Math.pow(2, qHeapLevel);
            qHeapLevel = qHeapLevel + 1;
            qHeap = Arrays.copyOf(qHeap, qHeap.length + addCount);
//            System.out.println("qHeap array increased by " + 
//                addCount + " elements.");
            
        }
        
        // Insert node
        qHeap[qHeapCount] = value;
        
        // Increment count
        qHeapCount = qHeapCount + 1;
        
        // Loop to filter node up to correct position
        // Loop continues as long as root not passed and child value 
        // larger than parent.  Child and parent are pointers using 
        // logic that assigns:
        // -Root node a value of 1.
        // -Its children values of 2 and 3, respectively.
        // -Its children values of 4, 5, 6, and 7, respectively.
        // -Etc.
        child = qHeapCount;
        parent = child / 2;
        while (parent > 0 && (qHeap[child - 1] > qHeap[parent - 1]))
        {

            // Swap parent and child nodes
            temp = qHeap[parent - 1];
            qHeap[parent - 1] = qHeap[child - 1];
            qHeap[child - 1] = temp;
            
            // Set new child and parent
            child = parent;
            parent = child / 2;

        }

    }

    //----------------------------------------------------------------
    // removeValueOrdered
    //----------------------------------------------------------------
    private static int removeValueOrdered()
    {
        
        // Declare variables
        int value;
        
        // Test if queue is empty
        if (qOrderedHead == -1)
            return -1;
        else
        {
            
            // Decrement count
            qOrderedCount = qOrderedCount - 1;

            // Remove node
            value = qOrdered[qOrderedHead];

            // Update qOrderedHead and qOrderedTail pointers
            if (qOrderedHead == qOrderedTail)
            {
                qOrderedHead = -1;
                qOrderedTail = -1;
            }
            else
                qOrderedHead = incrementPointer(qOrderedHead);
            return value;
            
        }

    }

    //----------------------------------------------------------------
    // removeValueUnordered
    //----------------------------------------------------------------
    private static int removeValueUnordered()
    {
        
        // Declare variables
        int value;
        int valuePtr;
        int curr;
        int prev;
        int copyLeft;
        int copyRight;

        // Test if queue is empty
        if (qUnorderedHead == -1)
            return -1;
        
        else
        {
            
            // Decrement count
            qUnorderedCount = qUnorderedCount - 1;
            
            // Test if queue has one node
            if (qUnorderedHead == qUnorderedTail)
            {
                value = qUnordered[qUnorderedHead];
                qUnorderedHead = -1;
                qUnorderedTail = -1;
                return value;
            }

            // Handle queue with more than one node
            else
            {

                // Loop to locate spot to remove node
                prev = qUnorderedHead;
                curr = incrementPointer(qUnorderedHead);                    
                valuePtr = qUnorderedHead;
                while (prev != qUnorderedTail)
                {
                    if (qUnordered[curr] > qUnordered[valuePtr]) 
                        valuePtr = curr;
                    prev = incrementPointer(prev);
                    curr = incrementPointer(curr);
                }
                value = qUnordered[valuePtr];
                
                // Test whether to remove node at tail
                if (valuePtr == qUnorderedTail)
                    qUnorderedTail = decrementPointer(qUnorderedTail);
                
                // Remove node
                else
                {
                    copyLeft = valuePtr;
                    copyRight = incrementPointer(copyLeft);
                    while (copyLeft != qUnorderedTail)
                    {
                        qUnordered[copyLeft] = qUnordered[copyRight];
                        copyLeft = incrementPointer(copyLeft);
                        copyRight = incrementPointer(copyRight);
                    }
                    qUnorderedTail = decrementPointer(qUnorderedTail);
                }

            }
            
            return value;

        }

    }

    //----------------------------------------------------------------
    // removeValueHeap
    //----------------------------------------------------------------
    private static int removeValueHeap()
    {
        
        // Declare variables
        int value;
        int parent;
        int childLeft;
        int childRight;
        int temp;

        // Test if queue is empty
        if (qHeapCount == 0)
            return -1;
        
        else
        {

            // Get value
            value = qHeap[0];

            // Replace root node with "last" node
            qHeap[0] = qHeap[qHeapCount - 1];
            
            // Decrement count
            qHeapCount = qHeapCount - 1;
            
            // Loop to filter node down to correct position
            // Loop continues as long a parent has two children
            // Child and parent are pointers using logic that assigns:
            // -Root node a value of 1.
            // -Its children values of 2 and 3, respectively.
            // -Its children values of 4, 5, 6, and 7, respectively.
            // -Etc.
            parent = 1;
            childLeft = 2;
            childRight = 3;
            while (childRight <= qHeapCount &&
                   (qHeap[parent - 1] < qHeap[childLeft - 1] || 
                    qHeap[parent - 1] < qHeap[childRight - 1]))
            {
                
                // Test which branch to filter node down to
                if (qHeap[childLeft - 1] > qHeap[childRight - 1])
                {

                    // Swap parent and left child nodes
                    temp = qHeap[parent - 1];
                    qHeap[parent - 1] = qHeap[childLeft - 1];
                    qHeap[childLeft - 1] = temp;
                    
                    // Set new parent to left child node
                    parent = childLeft;

                }
                else
                {

                    // Swap parent and right child nodes
                    temp = qHeap[parent - 1];
                    qHeap[parent - 1] = qHeap[childRight - 1];
                    qHeap[childRight - 1] = temp;
                    
                    // Set new parent to right child node
                    parent = childRight;
                    
                }
                
                // Set new children
                childLeft = parent * 2;
                childRight = (parent * 2) + 1;

            }
            
            // Test if parent has only left child
            if (childLeft == qHeapCount && 
                qHeap[parent - 1] < qHeap[childLeft - 1])
            {

                // Swap parent and left child nodes
                temp = qHeap[parent - 1];
                qHeap[parent - 1] = qHeap[childLeft - 1];
                qHeap[childLeft - 1] = temp;

            }
            
            return value;
            
        }
        
    }

    //----------------------------------------------------------------
    // main
    //----------------------------------------------------------------
    public static void main (String[] args)
    {

        // Declare variables
        Random rand = new Random();
        int nodes;
        int interval;
        String s;
        boolean print;
        Scanner keyboard = new Scanner(System.in);
        int addValue;
        int removeValue;
        long before;
        int removedValueOrdered = -1;
        int removedValueUnordered = -1;
        int removedValueHeap = -1;
        long insertTimeOrdered;
        long insertTimeUnordered;
        long insertTimeHeap;
        long removeTimeOrdered;
        long removeTimeUnordered;
        long removeTimeHeap;
        
        // Show application header
        System.out.println("Welcome to Priority Queue Using Arrays");
        System.out.println("--------------------------------------");

        // Prompt for and get number of nodes to add and then remove
        System.out.print(
            "\nEnter number of nodes to add and then remove." +
            "\n100K nodes takes ~15 seconds to add and then remove." +
            "\n1M nodes takes ~25 minutes but heap portion takes " +
            "<1 second: ");
        nodes = keyboard.nextInt();

        // Prompt for and get print interval
        System.out.print("\nEnter print interval." +
            "\nHigher interval means less output: ");
        interval = keyboard.nextInt();

        // Initialize insert times
        insertTimeOrdered = 0;
        insertTimeUnordered = 0;
        insertTimeHeap = 0;
        
        // Print node-add headings
        System.out.println("\nAdding " + 
            String.format(COLFMTD1, nodes) + " random values " +
            "to three data structures");
        System.out.println(DIVIDER);
        s = "  First " + MAX_PRINT;
        System.out.printf(COLFMTS1 + COLFMTS2 + COLFMTS2 + COLFMTS2 +
            COLFMTS2 + COLFMTS3 + "%n",
            "Array", "Queue", "Queue", "Head", "Tail", s);
        System.out.printf(COLFMTS1 + COLFMTS2 + COLFMTS2 + COLFMTS2 +
            COLFMTS2 + COLFMTS3 + "%n",
            "Type", "Capacity", "Nodes", "Pointer", "Pointer", 
            "  Values");
        
        // Loop to add nodes
        for (int i = 1; i <= nodes; i++)
        {
            
            // Generate random value
            addValue = rand.nextInt(RAND_UPPER_LIMIT);

            // Test whether to print value to be added
            print = (i % interval == 0);
            if (print) 
                System.out.println("\nNode " + 
                    String.format(COLFMTD1, i) + " with value " + 
                    addValue + " to be added.");
            
            // Insert into ordered array
            before = System.currentTimeMillis();
            insertValueOrdered(addValue);
            insertTimeOrdered = insertTimeOrdered + 
                (System.currentTimeMillis() - before);
            listNodes("Ordered", qOrdered, qOrderedCount,
                qOrderedHead, qOrderedTail, print);

            // Insert into unordered array
            before = System.currentTimeMillis();
            insertValueUnordered(addValue);
            insertTimeUnordered = insertTimeUnordered + 
                (System.currentTimeMillis() - before);
            listNodes("Unordered", qUnordered, qUnorderedCount, 
                qUnorderedHead, qUnorderedTail, print);
            
            // Insert into heap
            before = System.currentTimeMillis();
            insertValueHeap(addValue);
            insertTimeHeap = insertTimeHeap + 
                (System.currentTimeMillis() - before);
            listHeapNodes(print);
            
        }
        
        // Initialize remove times
        removeTimeOrdered = 0;
        removeTimeUnordered = 0;
        removeTimeHeap = 0;
        
        // Print node-remove headings
        System.out.println("\nRemoving " + 
            String.format(COLFMTD1, nodes) + 
            " highest-priority values from three data structures");
        System.out.println(DIVIDER);
        s = "  First " + MAX_PRINT;
        System.out.printf(COLFMTS1 + COLFMTS2 + COLFMTS2 + COLFMTS2 +
            COLFMTS2 + COLFMTS3 + "%n",
            "Array", "Queue", "Queue", "Head", "Tail", s);
        System.out.printf(COLFMTS1 + COLFMTS2 + COLFMTS2 + COLFMTS2 +
            COLFMTS2 + COLFMTS3 + "%n",
            "Type", "Capacity", "Nodes", "Pointer", "Pointer", 
            "  Values");

        // Loop to remove nodes
        for (int i = 1; i <= nodes; i++)
        {
            
            // Get value to be removed
            removeValue = qOrdered[qOrderedHead];

            // Test whether to print value to be removed
            print = (i % interval == 0);
            if (print) 
                System.out.println("\nNode " + 
                    String.format(COLFMTD1, i) + " with value " + 
                    removeValue + " to be removed.");
            
            // Remove from ordered array
            before = System.currentTimeMillis();
            removedValueOrdered = removeValueOrdered();
            removeTimeOrdered = removeTimeOrdered + 
                (System.currentTimeMillis() - before);
            listNodes("Ordered", qOrdered, qOrderedCount, 
                qOrderedHead, qOrderedTail, print);

            // Remove from unordered array
            before = System.currentTimeMillis();
            removedValueUnordered = removeValueUnordered();
            removeTimeUnordered = removeTimeUnordered + 
                (System.currentTimeMillis() - before);
            listNodes("Unordered", qUnordered, qUnorderedCount, 
                qUnorderedHead, qUnorderedTail, print);
            
            // Remove from heap
            before = System.currentTimeMillis();
            removedValueHeap = removeValueHeap();
            removeTimeHeap = removeTimeHeap + 
                (System.currentTimeMillis() - before);
            listHeapNodes(print);

        }
        
        // Print timings
        System.out.println("\nTotal add and remove times for " + 
            String.format(COLFMTD1, nodes) + 
            " nodes for three data structures");
        System.out.println(DIVIDER);
        System.out.printf(COLFMTS1 + COLFMTS4 + COLFMTS4 + COLFMTS4 +
            "%n", "Array", "Total Add", "Total Remove", "Total");
        System.out.printf(COLFMTS1 + COLFMTS4 + COLFMTS4 + COLFMTS4 +
            "%n", "Type", "Time (ms)", "Time (ms)", "Time (ms)");

        // Print times
        System.out.printf(COLFMTS1 + COLFMTD3 + COLFMTD3 + COLFMTD3 + 
            "%n", "Ordered", insertTimeOrdered, removeTimeOrdered, 
            (insertTimeOrdered + removeTimeOrdered));
        System.out.printf(COLFMTS1 + COLFMTD3 + COLFMTD3 + COLFMTD3 + 
            "%n", "Unordered", insertTimeUnordered, removeTimeUnordered, 
            (insertTimeUnordered + removeTimeUnordered));
        System.out.printf(COLFMTS1 + COLFMTD3 + COLFMTD3 + COLFMTD3 + 
            "%n", "Heap", insertTimeHeap, removeTimeHeap, 
            (insertTimeHeap + removeTimeHeap));

        // Show application close
        System.out.println("\nEnd of Priority Queue Using Arrays");

    }

}
