module mouse;

import core.sys.windows.windows;
import std.stdio;
import std.conv;
import core.thread;

void main()
{
    POINT cursorPos;
    int lastX = 6969;
    int lastY = 6969;

    for (;;) {
        if (GetAsyncKeyState('K') & 1)
        {
            if (GetCursorPos(&cursorPos))
            {
                if (lastX == 6969 && lastY == 6969)
                {
                    lastX = cursorPos.x;
                    lastY = cursorPos.y;
                }
                else
                {
                    int deltaX, deltaY;
                    deltaX = cursorPos.x - lastX;
                    deltaY = cursorPos.y - lastY;
                    writeln(lastX.to!string ~ ", " ~ lastY.to!string ~ ", " ~ deltaX.to!string ~ ", " ~ deltaY.to!string);
                    lastX = 6969;
                    lastY = 6969;
                }
            }
        }
        Thread.sleep(dur!("msecs")(200));
    }
}