input = open("input.txt", "r")
input = input.read()
pairs = { "(": ")", "[": "]", "{": "}", "<": ">" }
scores = { ")": 3, "]": 57, "}": 1197, ">": 25137 }
score = 0
for line in input.split("\n"):
    stack = []
    for char in line:
        if char in pairs:
            stack.append(char)
        else:
            if stack and pairs[stack[-1]] == char:
                stack.pop()
            else:
                score += scores[char]
                break
print(score)