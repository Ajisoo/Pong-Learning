# Pong-Learning
A basic example of a working genetic algorithm on a neural network to learn how to play Pong.

In this network, there are 3 inputs and 1 output. The three inputs are bias, paddle-y, and ball-y. 
While bias is likely unecessary, I wanted to see how the network learned to use necessary and unnecessary information.
The singular output is the probability of moving up. This value is bounded to be between 0 and 1, and represents the likely-hood of moving up.

The structure of the network is 3 inputs to 1 "hidden" node, and 1 hidden node to 1 output. 
The hidden node is uncessary, but I wanted to see how the program worked around this and what potential issues my code had with hidden layers.

The results (having the program run overnight) resulted in a huge success. Not only were the networks beating the AI, they were playing nearly perfectly.

This program is more of a "proof of theory" and testing grounds.
