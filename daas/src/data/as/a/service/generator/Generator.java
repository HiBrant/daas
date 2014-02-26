package data.as.a.service.generator;

import org.objectweb.asm.Opcodes;

public interface Generator extends Opcodes {

	byte[] generate();
}
