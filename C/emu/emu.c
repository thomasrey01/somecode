#include <stdio.h>
#include <stdint.h>

/* MASKS:
 * 0x07: 00000111
 * 0x38: 00111000
 */

typedef struct ConditionCodes {
	uint8_t	z:1;
	uint8_t s:1;
	uint8_t p:1;
	uint8_t cy:1;
    uint8_t ac:1;
	uint8_t pad:3;		
} ConditionCodes;

typedef struct State8080
{
	uint8_t a;
	uint8_t b; 
	uint8_t c; 
	uint8_t d; 
	uint8_t e;
	uint8_t h;
	uint8_t l;
	uint16_t sp;
    uint16_t pc;
	uint8_t *memory;
	struct ConditionCodes cc;
	uint8_t int_enable;	
} State8080;

void move(State8080* state)
{
	unsigned char *opcode = &state->memory[state->pc];
	
	uint8_t reg1 = (((*opcode & 0x38) >> 3) + 1) % 8;
	uint8_t reg2 = ((*opcode & 0x07) + 1) % 8;
	*(state + (reg2 * sizeof(uint8_t))) = *(state + (reg1 * sizeof(uint8_t)));
}

void add(State8080* state)
{
	unsigned char *opcode = &state->memory[state->pc];
	uint8_t reg = ((*opcode & 0x07) + 1) % 8;
	uint16_t val = (uint16_t)*(state + (reg * sizeof(uint8_t)));

}

int Emulate8080p (State8080* state)
{
	unsigned char *opcode = &state->memory[state->pc];

	switch(*opcode) {
		case 0x00: break;
		case 0x01:
			state->c = opcode[1];
			state->b = opcode[2];
			state->pc += 2;
			break;
		case 0x02:
		case 0x03:
		case 0x04:
		case 0x05:
		case 0x06:
		case 0x07:
		case 0x08:
		case 0x09:
		case 0x0a:
		case 0x0b:
		case 0x0c:
		case 0x0d:
		case 0x0e:
		case 0x0f:
		case 0x10:
		case 0x11:
		case 0x12:
		case 0x13:
		case 0x14:
		case 0x15:
		case 0x16:
		case 0x17:
		case 0x18:
		case 0x19:
		case 0x1a:
		case 0x1b:
		case 0x1c:
		case 0x1d:
		case 0x1e:
		case 0x1f:
		case 0x20:
		case 0x21:
		case 0x22:
		case 0x23:
		case 0x24:
		case 0x25:
		case 0x26:
		case 0x27:
		case 0x28:
		case 0x29:
		case 0x2a:
		case 0x2b:
		case 0x2c:
		case 0x2d:
		case 0x2e:
		case 0x2f:
		case 0x30:
		case 0x31:
		case 0x32:
		case 0x33:
		case 0x34:
		case 0x35:
		case 0x36:
		case 0x37:
		case 0x38:
		case 0x39:
		case 0x3a:
		case 0x3b:
		case 0x3c:
		case 0x3d:
		case 0x3e:
		case 0x3f:
		case 0x40: state->b = state->b;
		case 0x41: state->b = state->c;
		case 0x42: state->b = state->d;
		case 0x43: state->b = state->e;
		case 0x44: state->b = state->h;
		case 0x45: state->b = state->l;
		case 0x46: break;
		case 0x47: state->b = state->a;
		case 0x48: state->c = state->b;
		case 0x49: state->c = state->c;
		case 0x4a: state->c = state->d;
		case 0x4b: state->c = state->e;
		case 0x4c: state->c = state->h;
		case 0x4d: state->c = state->l;
		case 0x4e: break;
		case 0x4f: state->c = state->a;
		case 0x50: state->d = state->b;
		case 0x51: state->d = state->c;
		case 0x52: state->d = state->d;
		case 0x53: state->d = state->e;
		case 0x54: state->d = state->h;
		case 0x55: state->d = state->l;
		case 0x56: break;
		case 0x57: state->d = state->a;
		case 0x58: state->e = state->b;
		case 0x59: state->e = state->c;
		case 0x5a: state->e = state->d;
		case 0x5b: state->e = state->e;
		case 0x5c: state->e = state->h;
		case 0x5d: state->e = state->l;
		case 0x5e: break;
		case 0x5f: state->e = state->a;
		case 0x60: state->h = state->b;
		case 0x61: state->h = state->c;
		case 0x62: state->h = state->d;
		case 0x63: state->h = state->e;
		case 0x64: state->h = state->h;
		case 0x65: state->h = state->l;
		case 0x66: break;
		case 0x67: state->h = state->a;
		case 0x68: state->l = state->b;
		case 0x69: state->l = state->c;
		case 0x6a: state->l = state->d;
		case 0x6b: state->l = state->e;
		case 0x6c: state->l = state->h;
		case 0x6d: state->l = state->l;
		case 0x6e: break;
		case 0x6f: state->l = state->a;
		case 0x70: break;
		case 0x71: break;
		case 0x72: break;
		case 0x73: break;
		case 0x74: break;
		case 0x75: break;
		case 0x76: break;
		case 0x77: break;
		case 0x78: state->a = state->b;
		case 0x79: state->a = state->c;
		case 0x7a: state->a = state->d;
		case 0x7b: state->a = state->e;
		case 0x7c: state->a = state->h;
		case 0x7d: state->a = state->l;
		case 0x7e: break;
		case 0x7f: 
		case 0x80: //ADD B
			uint16_t answer = (uint16_t) state->a + (uint16_t) state->b;
			state->cc.z = ((answer & 0xff) == 0);
			state->cc.s = ((answer & 0x80) != 0);
			state->cc.cy = (answer > 0xff);
			state->cc.p = !((answer & 0xff) % 2);
			state->a = answer & 0xff;
		case 0x81:
			uint16_t answer = (uint16_t) state->a + (uint16_t) state->c;
			state->cc.z = ((answer & 0xff) == 0);
			state->cc.s = ((answer & 0x80) != 0);
			state->cc.cy = (answer > 0xff);
			state->cc.p = !((answer & 0xff) % 2);
			state->a = answer & 0xff;
		case 0x82:
			uint16_t answer = (uint16_t) state->a + (uint16_t) state->d;
			state->cc.z = ((answer & 0xff) == 0);
			state->cc.s = ((answer & 0x80) != 0);
			state->cc.cy = (answer > 0xff);
			state->cc.p = !((answer & 0xff) % 2);
			state->a = answer & 0xff;
		case 0x83:
			uint16_t answer = (uint16_t) state->a + (uint16_t) state->b;
			state->cc.z = ((answer & 0xff) == 0);
			state->cc.s = ((answer & 0x80) != 0);
			state->cc.cy = (answer > 0xff);
			state->cc.p = !((answer & 0xff) % 2);
			state->a = answer & 0xff;
		case 0x84:
		case 0x85:
		case 0x86:
		case 0x87:
		case 0x88:
		case 0x89:
		case 0x8a:
		case 0x8b:
		case 0x8c:
		case 0x8d:
		case 0x8e:
		case 0x8f:
		case 0x90:
		case 0x91:
		case 0x92:
		case 0x93:
		case 0x94:
		case 0x95:
		case 0x96:
		case 0x97:
		case 0x98:
		case 0x99:
		case 0x9a:
		case 0x9b:
		case 0x9c:
		case 0x9d:
		case 0x9e:
		case 0x9f:
		case 0xa0:
		case 0xa1:
		case 0xa2:
		case 0xa3:
		case 0xa4:
		case 0xa5:
		case 0xa6:
		case 0xa7:
		case 0xa8:
		case 0xa9:
		case 0xaa:
		case 0xab:
		case 0xac:
		case 0xad:
		case 0xae:
		case 0xaf:
		case 0xb0:
		case 0xb1:
		case 0xb2:
		case 0xb3:
		case 0xb4:
		case 0xb5:
		case 0xb6:
		case 0xb7:
		case 0xb8:
		case 0xb9:
		case 0xba:
		case 0xbb:
		case 0xbc:
		case 0xbd:
		case 0xbe:
		case 0xbf:
		case 0xc0:
		case 0xc1:
		case 0xc2:
		case 0xc3:
		case 0xc4:
		case 0xc5:
		case 0xc6:
		case 0xc7:
		case 0xc8:
		case 0xc9:
		case 0xca:
		case 0xcb:
		case 0xcc:
		case 0xcd:
		case 0xce:
		case 0xcf:
		case 0xd0:
		case 0xd1:
		case 0xd2:
		case 0xd3:
		case 0xd4:
		case 0xd5:
		case 0xd6:
		case 0xd7:
		case 0xd8:
		case 0xd9:
		case 0xda:
		case 0xdb:
		case 0xdc:
		case 0xdd:
		case 0xde:
		case 0xdf:
		case 0xe0:
		case 0xe1:
		case 0xe2:
		case 0xe3:
		case 0xe4:
		case 0xe5:
		case 0xe6:
		case 0xe7:
		case 0xe8:
		case 0xe9:
		case 0xea:
		case 0xeb:
		case 0xec:
		case 0xed:
		case 0xee:
		case 0xef:
		case 0xf0:
		case 0xf1:
		case 0xf2:
		case 0xf3:
		case 0xf4:
		case 0xf5:
		case 0xf6:
		case 0xf7:
		case 0xf8:
		case 0xf9:
		case 0xfa:
		case 0xfb:
		case 0xfc:
		case 0xfd:
		case 0xfe:
		case 0xff:
		default:
			break;
	}
}
