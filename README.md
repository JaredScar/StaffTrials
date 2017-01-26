# StaffTrials

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

[RankTrials Bukkit Plugin Page] (https://dev.bukkit.org/projects/ranktrials)

RankTrials was a plugin that was made for this request. It was originally going to be called StaffTrials, however it now has many uses and can be used to count down kicking a player or banning a player by running the commands.

<h2>Permissions</h2>
sTrial.Admin - Permission to run the command /sTrial

<h2>Commands</h2>
/sTrial add (TrialType) (Time) (Player) (Command) - This adds a trial to a player and performs the command when the time is met. The command supports spaces, but can not include a / in the beginning.

/sTrial cancel (Player) - Cancels the trial of the specified player. Will force run the command that was defined for that sTrial right away.

<h2>Config</h2>
Messages:

  &nbsp;&nbsp;&nbsp;&nbsp;TimeExpired: '&4Your trial has ended for your staff rank! :( &aWe will get back to you on how you did soon enough! :)'
  
Players: {}
