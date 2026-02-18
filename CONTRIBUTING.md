# Contributing Guide

Before you start working on the project, configure Git so your commits use your school GitLab identity. Run these commands inside the project folder:

```bash
git config user.name "Gitlabs.username"
git config user.email "schoolemail@uib.no"
git config --list
```

## Core rules

### Branching

1. Never work on or push directly to main

2. Always work in your own branch

3. One feature or fix per branch

**Branch naming:** username/short-description

**Example:** MartinDrevdal/player-movement

### Commits

1. English only

2. Present tense

3. Keep commits small and meaningful

Example: Adding jump logic, Fixing collision bug

### Before merge

1. Project runs locally

2. Up to date with target branch (main)

3. No debug prints or unused code

4. Review approved

### Merge Requests

1. All code goes through a Merge Request

2. MR must explain: What was changed, and why

## Typical workflow

### Setting up new branch (from latest main)

```bash
git switch main
git pull
git switch -c username/branch_name

```

### Sync your branch with main (do this frequently)

```bash
git switch main
git pull
git switch username/short-description
git rebase main
```
