(PRTLOOP)
    @SCREEN
    D=A
    @0
    M=D         // RAM[0] sei mom. Pixel, beginne mit @SCREEN-Adresse
    @KEYBOARD
    D=M
    @PRTWHITE
    D;JEQ       // Weiß printen, falls Adresse Keyboard-Eingabe, sonst schwarz
    @PRTBLACK
    0;JMP
(PRTWHITE)
    @0
    D=M         // Momentanes Pixel in D speichern
    @KEYBOARD
    D=M-D       // Sind noch Pixel bis Beginn von Keyboard-Adressen übrig?
    @PRTLOOP
    D;JGT
    @PRINTPIXELWHITE
    0;JMP       // Falls ja: Momentanes Pixel weiß machen
(PRINTPIXELWHITE)
    @0
    D=M         // D sein nun Adr. des momentanen Pixels
    A=D         // Adresse von M sei diese
    M=0         //Inhalt der Adresse weiß machen
    @0
    M=M+1       // Momentanes Pixel inkrementieren
    @PRTWHITE
    0;JMP
(PRTBLACK)
    @0
    D=M
    @KEYBOARD
    D=M-D
    @PRTLOOP
    D;JGT
    @PRINTPIXELBLACK
    0;JMP
(PRINTPIXELBLACK)
    @0
    D=AM
    A=D
    M=1
    @0
    M=M+1
    @PRTWHITE
    0;JMP
