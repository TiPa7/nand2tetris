@10
D=A
@SP
AM=M+1
A=A-1
M=D //push constant 10
@0
D=A
@LCL
D=D+M
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
M=D //pop local 0
@21
D=A
@SP
AM=M+1
A=A-1
M=D //push constant 21
@22
D=A
@SP
AM=M+1
A=A-1
M=D //push constant 22
@2
D=A
@ARG
D=D+M
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
M=D //pop argument 2
@1
D=A
@ARG
D=D+M
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
M=D //pop argument 1
@36
D=A
@SP
AM=M+1
A=A-1
M=D //push constant 36
@6
D=A
@THIS
D=D+M
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
M=D //pop this 6
@42
D=A
@SP
AM=M+1
A=A-1
M=D //push constant 42
@45
D=A
@SP
AM=M+1
A=A-1
M=D //push constant 45
@5
D=A
@THAT
D=D+M
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
M=D //pop that 5
@2
D=A
@THAT
D=D+M
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
M=D //pop that 2
@510
D=A
@SP
AM=M+1
A=A-1
M=D //push constant 510
@6
D=A
@5
D=D+A
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
M=D //pop temp 6
@LCL
D=M
@0
A=D+A
D=M
@SP
AM=M+1
A=A-1
M=D //push local 0
@THAT
D=M
@5
A=D+A
D=M
@SP
AM=M+1
A=A-1
M=D //push that 5
@SP
AM=M-1
D=M
A=A-1
M=M+D //add
@ARG
D=M
@1
A=D+A
D=M
@SP
AM=M+1
A=A-1
M=D //push argument 1
@SP
AM=M-1
D=M
A=A-1
M=M-D //sub
@THIS
D=M
@6
A=D+A
D=M
@SP
AM=M+1
A=A-1
M=D //push this 6
@THIS
D=M
@6
A=D+A
D=M
@SP
AM=M+1
A=A-1
M=D //push this 6
@SP
AM=M-1
D=M
A=A-1
M=M+D //add
@SP
AM=M-1
D=M
A=A-1
M=M-D //sub
@5
D=A
@6
A=D+A
D=M
@SP
AM=M+1
A=A-1
M=D //push temp 6
@SP
AM=M-1
D=M
A=A-1
M=M+D //add