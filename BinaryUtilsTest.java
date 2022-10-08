import Communication.BinaryUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BinaryUtilsTest {


    @Test
    @DisplayName("Hello")
    @BeforeEach
    void vectorXor()
    {
        assertAll(() -> assertEquals(0b000, BinaryUtils.xor(0b0000000,0b1011)[1]),
        () -> assertEquals(0b011, BinaryUtils.xor(0b0001000,0b1011)[1]),
        () -> assertEquals(0b110, BinaryUtils.xor(0b0010000,0b1011)[1]),
        () -> assertEquals(0b101, BinaryUtils.xor(0b0011000,0b1011)[1]),
        () -> assertEquals(0b111, BinaryUtils.xor(0b0100000,0b1011)[1]),
        () -> assertEquals(0b100, BinaryUtils.xor(0b0101000,0b1011)[1]),
        () -> assertEquals(0b001, BinaryUtils.xor(0b0110000,0b1011)[1]),
        () -> assertEquals(0b010, BinaryUtils.xor(0b0111000,0b1011)[1]),
        () -> assertEquals(0b101, BinaryUtils.xor(0b1000000,0b1011)[1]),
        () -> assertEquals(0b110, BinaryUtils.xor(0b1001000,0b1011)[1]),
        () -> assertEquals(0b011, BinaryUtils.xor(0b1010000,0b1011)[1]),
        () -> assertEquals(0b000, BinaryUtils.xor(0b1011000,0b1011)[1]),
        () ->  assertEquals(0b010, BinaryUtils.xor(0b1100000,0b1011)[1]),
        () -> assertEquals(0b001, BinaryUtils.xor(0b1101000,0b1011)[1]),
        () -> assertEquals(0b100, BinaryUtils.xor(0b1110000,0b1011)[1]),
        () -> assertEquals(0b111, BinaryUtils.xor(0b1111000,0b1011)[1]));
    }
}
