# CM30229 - Circumnavigation using Lejos

## Introduction

The goal of this research is to see the effect of changing the rate of sensing on the navigation capabilities of a rover. The rover is built using parts from the LEGO Mindstorm NXJ kit. Four sensors are utilised for sensing; a Sonar, Light Sensor and Bump Sensor (x2), and three motors provide movement.
The sonar is mounted on a pivot to allow for measurements in multiple directions, as shown in figure 1. Two driven wheels are used for drive and a trolley wheel is placed at the back for stability.

Figure 1: Rover Head Sensor
![Rover Head Sensor](https://github.com/domhauton/CM30229-Intelligent-Control-Systems-Lejos/blob/master/writeup/dominic/img/headshot.JPG)

During development a reactive approach is taken, using the subsumption architecture [Wooldridge, 2009] to allow the rover to determine its current situation.
The use of the subsumption architecture is based on Brooks [1991]’ thesis that Intelligence is an emergent property of certain complex systems. During development this subsumption approach is added into the perception mechanism.

## Approach

The initial design of the rover is a simple reflex agent as described by Russell et al. [1995]. This takes numerical sensor readings, and uses transduction converting them to a qualitative proximity label in every cardinal direction, making immediate decisions based on these readings. This allows the rover to react to a crash but gives no context for recovery.
The final design uses Russell et al. [1995]’s model-based reflex agent. This allows the sensors to modify the state at individual rates. There are two sensing thread loops as shown in figure 2. This is done to prevent the sonar slowing down other sensors.

As the front sensor uses three (Bump, light and sonar) separate sensors for proximity detection the closest, most important, result is taken. To improve sensor quality 15 readings are taken every measurement and any reading more than two standard deviations away from the mean is thrown out. The remainder of the readings are averaged.
To account for arena variances, a calibration routine is used. This requests several rover placements a given distance from the arena wall on each side.

Figure 2: Final Perception Architecture
![Rover Head Sensor](https://github.com/domhauton/CM30229-Intelligent-Control-Systems-Lejos/blob/master/writeup/dominic/img/sensing-diagram.jpg)

A decision loop reads the state and determines a meta-state, (such as TOO_CLOSE_TO_WALL or WALL_NOT_FOUND,) from the current state using a subsumption architecture. If the rover is NEAR something to the front, it has crashed and it does not matter that it is NEAR an object on the side too. This meta-state is then used in the action planner, which selects the best action given a meta-state. If the rover is crashed and it is NEAR an object to the left, it does not try to reverse to the left.

The meta-state also directs the sonar. If it is following a wall on the left and has found the wall, it skips the reading on the right as it is irrelevant to the navigation. This system describes the platform for experimentation. The goal is to find if there is an optimal sensing rate.

The rover is run at a tick rate (measurements per second) of 5, 25 and 45. During a run the rover performs 10 laps of the test track (fig. 3), and the number of bump sensor activations was recorded programmatically.

Each run is done twice. The hypothesis is that the rover will perform better with a higher tick rate, but with diminishing returns. If the rover is stuck in a corner, the experiment is restarted. During each run, the rover is started in the bottom left hand corner of the arena (fig. 3) facing the left wall. The experiment is conducted with a variety of obstacle types and sizes to produce a generalised result.

Figure 3: Test Area Layout
![Rover Head Sensor](https://github.com/domhauton/CM30229-Intelligent-Control-Systems-Lejos/blob/master/writeup/dominic/img/test-area-layout.png)

The rover was shared with Ryan Cullen. Dominic Hauton built the rover itself, optimising it’s design and creating the perception system in the code, which continuously modifies proximity readings in the state, indicating how close the rover is to an object in all four cardinal directions. At this point Ryan forked the code and action selection is developed individually.

Figure 4: Stanley Rover
![Rover Head Sensor](https://github.com/domhauton/CM30229-Intelligent-Control-Systems-Lejos/blob/master/writeup/dominic/img/full.JPG)

## Results

The results of the experiment are consistent with the hypothesis. With this positive result the experiment is also conducted after doubling the rover movement speed. A higher tick rate is found to help the rover at higher speeds, leading to the conclusion that the faster the rover moves, the faster it needs to sense the environment around it.
As the results for one lap are inconsistent, the rover performs two runs of 10 laps. Both 10 lap runs were within 2 bumps per lap of each other on average, which was within the required 0.10 p-value for statistical significance.

Table 1: Bumps per lap at two speeds

| Tickrate |   5   |  25   |  45   |
| -------- |:-----:|:-----:|:-----:|
| Slow     | 23 ±2 | 14 ±0 | 16 ±1 |
| Fast	   | 53 ±3 | 46 ±2 | 26 ±2 |

The video found at [youtu.be/LOPdO0w1Uec](https://youtu.be/LOPdO0w1Uec) depicts the start of a trail run.

## Discussion

The results closely match the hypothesis, however, the sensing speed of the sonar, which is the key part of the navigation logic, is limited due to physical speed. A second sonar that does not require physical movement for sensing may improve the navigation of the rover.
Although the arena environment was kept as consistent as possible, the light sensor is very susceptible to bright light and occasionally reacted to the room light being in front of it. To make sonar readings consistent the arena is covered with hard surfaces, but even with the arena, materials of different hardness result in different distance readings.
The main take away from the research, is that to improve navigation of a rover; sensing should be increased as far as they add a tangible benefit to navigation, and the faster the movement the more important this is. In applications such as quadcopters loop times affect flight characteristics, but enabling faster loop times might mean disabling other features so this left adjustable in drone software for users. [Betaflight, 2017]. It is important for these users to understand the significance of this sensing rate change.

## Conclusion

This research set out to find the impact of sensing speed on the navigation of rovers. The results show that a faster sensing loop results in more accurate navigation with diminishing returns.
When deciding on sensing loop speeds, this level should be reached and not surpassed, as going too high might take compute cycles away from other important parts of the planning loop.

## Bibliography

   Betaflight. Betaflight FAQ. https://github.com/betaflight/betaflight/wiki/Frequently-_Asked-_Questions#what-_is-_2khz-_mode-_, 2017. [Online; accessed 28-Feb-2017].

   Rodney A Brooks. Intelligence without representation. Artificial intelligence, 47 (1-3):139–159, 1991.

   Stuart Russell, Peter Norvig, and Artificial Intelligence. A modern approach. Artificial Intelligence. Prentice-Hall, Egnlewood Cliffs, 25:27, 1995.

   Michael Wooldridge. An introduction to multiagent systems. John Wiley & Sons, 2009.
