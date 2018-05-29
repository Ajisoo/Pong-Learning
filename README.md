# Pong-Learning
A basic example of a working genetic algorithm on a neural network to learn how to play Pong.

In this network, there are 3 inputs and 1 output. The three inputs are bias, paddle-y, and ball-y. 
While bias is likely unecessary, I wanted to see how the network learned to use necessary and unnecessary information.
The singular output is the probability of moving up. This value is bounded to be between 0 and 1, and represents the likely-hood of moving up.

The structure of the network is 3 inputs to 1 "hidden" node, and 1 hidden node to 1 output. 
The hidden node is uncessary, but I wanted to see how the program worked around this and what potential issues my code had with hidden layers.

The results (having the program run overnight) resulted in a huge success. Not only were the networks beating the AI, they were playing nearly perfectly.

This program is more of a "proof of theory" and testing grounds.

Instructions:
The learning process is purely command line and only output.
The creation of weights can be turned on or off by commenting out the line of code.
From then on, each generation will put put into console and the new weights will be saved.
While there is a hard-cap on the number of generations, simply shutting down the program will stop it early with no issues. (The most recent completed generation is saved to disk).
To view a game, run ViewExampleGame.main() and specify which weight to see. This opens up a Java window and displays the pong game.
