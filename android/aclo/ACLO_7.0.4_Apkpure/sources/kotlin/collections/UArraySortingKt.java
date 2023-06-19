package kotlin.collections;

import kotlin.Metadata;
import kotlin.UByte;
import kotlin.UByteArray;
import kotlin.UIntArray;
import kotlin.ULongArray;
import kotlin.UShort;
import kotlin.UShortArray;
import kotlin.UnsignedKt;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: UArraySorting.kt */
@Metadata(d1 = {"\u00000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0010\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0006\u0010\u0007\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\t\u0010\n\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\f\u0010\r\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u000e2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u000f\u0010\u0010\u001a*\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0013\u0010\u0014\u001a*\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0015\u0010\u0016\u001a*\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0017\u0010\u0018\u001a*\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000e2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0019\u0010\u001a\u001a*\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u0001H\u0001ø\u0001\u0000¢\u0006\u0004\b\u001e\u0010\u0014\u001a*\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u0001H\u0001ø\u0001\u0000¢\u0006\u0004\b\u001f\u0010\u0016\u001a*\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000b2\u0006\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u0001H\u0001ø\u0001\u0000¢\u0006\u0004\b \u0010\u0018\u001a*\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000e2\u0006\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u0001H\u0001ø\u0001\u0000¢\u0006\u0004\b!\u0010\u001a\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\""}, d2 = {"partition", "", "array", "Lkotlin/UByteArray;", "left", "right", "partition-4UcCI2c", "([BII)I", "Lkotlin/UIntArray;", "partition-oBK06Vg", "([III)I", "Lkotlin/ULongArray;", "partition--nroSd4", "([JII)I", "Lkotlin/UShortArray;", "partition-Aa5vz7o", "([SII)I", "quickSort", "", "quickSort-4UcCI2c", "([BII)V", "quickSort-oBK06Vg", "([III)V", "quickSort--nroSd4", "([JII)V", "quickSort-Aa5vz7o", "([SII)V", "sortArray", "fromIndex", "toIndex", "sortArray-4UcCI2c", "sortArray-oBK06Vg", "sortArray--nroSd4", "sortArray-Aa5vz7o", "kotlin-stdlib"}, k = 2, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class UArraySortingKt {
    /* renamed from: partition-4UcCI2c  reason: not valid java name */
    private static final int m464partition4UcCI2c(byte[] bArr, int i, int i2) {
        int i3;
        byte m87getw2LRezQ = UByteArray.m87getw2LRezQ(bArr, (i + i2) / 2);
        while (i <= i2) {
            while (true) {
                int m87getw2LRezQ2 = UByteArray.m87getw2LRezQ(bArr, i) & UByte.MAX_VALUE;
                i3 = m87getw2LRezQ & UByte.MAX_VALUE;
                if (Intrinsics.compare(m87getw2LRezQ2, i3) >= 0) {
                    break;
                }
                i++;
            }
            while (Intrinsics.compare(UByteArray.m87getw2LRezQ(bArr, i2) & UByte.MAX_VALUE, i3) > 0) {
                i2--;
            }
            if (i <= i2) {
                byte m87getw2LRezQ3 = UByteArray.m87getw2LRezQ(bArr, i);
                UByteArray.m92setVurrAj0(bArr, i, UByteArray.m87getw2LRezQ(bArr, i2));
                UByteArray.m92setVurrAj0(bArr, i2, m87getw2LRezQ3);
                i++;
                i2--;
            }
        }
        return i;
    }

    /* renamed from: quickSort-4UcCI2c  reason: not valid java name */
    private static final void m468quickSort4UcCI2c(byte[] bArr, int i, int i2) {
        int m464partition4UcCI2c = m464partition4UcCI2c(bArr, i, i2);
        int i3 = m464partition4UcCI2c - 1;
        if (i < i3) {
            m468quickSort4UcCI2c(bArr, i, i3);
        }
        if (m464partition4UcCI2c < i2) {
            m468quickSort4UcCI2c(bArr, m464partition4UcCI2c, i2);
        }
    }

    /* renamed from: partition-Aa5vz7o  reason: not valid java name */
    private static final int m465partitionAa5vz7o(short[] sArr, int i, int i2) {
        int i3;
        short m347getMh2AYeg = UShortArray.m347getMh2AYeg(sArr, (i + i2) / 2);
        while (i <= i2) {
            while (true) {
                int m347getMh2AYeg2 = UShortArray.m347getMh2AYeg(sArr, i) & UShort.MAX_VALUE;
                i3 = m347getMh2AYeg & UShort.MAX_VALUE;
                if (Intrinsics.compare(m347getMh2AYeg2, i3) >= 0) {
                    break;
                }
                i++;
            }
            while (Intrinsics.compare(UShortArray.m347getMh2AYeg(sArr, i2) & UShort.MAX_VALUE, i3) > 0) {
                i2--;
            }
            if (i <= i2) {
                short m347getMh2AYeg3 = UShortArray.m347getMh2AYeg(sArr, i);
                UShortArray.m352set01HTLdE(sArr, i, UShortArray.m347getMh2AYeg(sArr, i2));
                UShortArray.m352set01HTLdE(sArr, i2, m347getMh2AYeg3);
                i++;
                i2--;
            }
        }
        return i;
    }

    /* renamed from: quickSort-Aa5vz7o  reason: not valid java name */
    private static final void m469quickSortAa5vz7o(short[] sArr, int i, int i2) {
        int m465partitionAa5vz7o = m465partitionAa5vz7o(sArr, i, i2);
        int i3 = m465partitionAa5vz7o - 1;
        if (i < i3) {
            m469quickSortAa5vz7o(sArr, i, i3);
        }
        if (m465partitionAa5vz7o < i2) {
            m469quickSortAa5vz7o(sArr, m465partitionAa5vz7o, i2);
        }
    }

    /* renamed from: partition-oBK06Vg  reason: not valid java name */
    private static final int m466partitionoBK06Vg(int[] iArr, int i, int i2) {
        int m165getpVg5ArA = UIntArray.m165getpVg5ArA(iArr, (i + i2) / 2);
        while (i <= i2) {
            while (UnsignedKt.uintCompare(UIntArray.m165getpVg5ArA(iArr, i), m165getpVg5ArA) < 0) {
                i++;
            }
            while (UnsignedKt.uintCompare(UIntArray.m165getpVg5ArA(iArr, i2), m165getpVg5ArA) > 0) {
                i2--;
            }
            if (i <= i2) {
                int m165getpVg5ArA2 = UIntArray.m165getpVg5ArA(iArr, i);
                UIntArray.m170setVXSXFK8(iArr, i, UIntArray.m165getpVg5ArA(iArr, i2));
                UIntArray.m170setVXSXFK8(iArr, i2, m165getpVg5ArA2);
                i++;
                i2--;
            }
        }
        return i;
    }

    /* renamed from: quickSort-oBK06Vg  reason: not valid java name */
    private static final void m470quickSortoBK06Vg(int[] iArr, int i, int i2) {
        int m466partitionoBK06Vg = m466partitionoBK06Vg(iArr, i, i2);
        int i3 = m466partitionoBK06Vg - 1;
        if (i < i3) {
            m470quickSortoBK06Vg(iArr, i, i3);
        }
        if (m466partitionoBK06Vg < i2) {
            m470quickSortoBK06Vg(iArr, m466partitionoBK06Vg, i2);
        }
    }

    /* renamed from: partition--nroSd4  reason: not valid java name */
    private static final int m463partitionnroSd4(long[] jArr, int i, int i2) {
        long m243getsVKNKU = ULongArray.m243getsVKNKU(jArr, (i + i2) / 2);
        while (i <= i2) {
            while (UnsignedKt.ulongCompare(ULongArray.m243getsVKNKU(jArr, i), m243getsVKNKU) < 0) {
                i++;
            }
            while (UnsignedKt.ulongCompare(ULongArray.m243getsVKNKU(jArr, i2), m243getsVKNKU) > 0) {
                i2--;
            }
            if (i <= i2) {
                long m243getsVKNKU2 = ULongArray.m243getsVKNKU(jArr, i);
                ULongArray.m248setk8EXiF4(jArr, i, ULongArray.m243getsVKNKU(jArr, i2));
                ULongArray.m248setk8EXiF4(jArr, i2, m243getsVKNKU2);
                i++;
                i2--;
            }
        }
        return i;
    }

    /* renamed from: quickSort--nroSd4  reason: not valid java name */
    private static final void m467quickSortnroSd4(long[] jArr, int i, int i2) {
        int m463partitionnroSd4 = m463partitionnroSd4(jArr, i, i2);
        int i3 = m463partitionnroSd4 - 1;
        if (i < i3) {
            m467quickSortnroSd4(jArr, i, i3);
        }
        if (m463partitionnroSd4 < i2) {
            m467quickSortnroSd4(jArr, m463partitionnroSd4, i2);
        }
    }

    /* renamed from: sortArray-4UcCI2c  reason: not valid java name */
    public static final void m472sortArray4UcCI2c(byte[] array, int i, int i2) {
        Intrinsics.checkNotNullParameter(array, "array");
        m468quickSort4UcCI2c(array, i, i2 - 1);
    }

    /* renamed from: sortArray-Aa5vz7o  reason: not valid java name */
    public static final void m473sortArrayAa5vz7o(short[] array, int i, int i2) {
        Intrinsics.checkNotNullParameter(array, "array");
        m469quickSortAa5vz7o(array, i, i2 - 1);
    }

    /* renamed from: sortArray-oBK06Vg  reason: not valid java name */
    public static final void m474sortArrayoBK06Vg(int[] array, int i, int i2) {
        Intrinsics.checkNotNullParameter(array, "array");
        m470quickSortoBK06Vg(array, i, i2 - 1);
    }

    /* renamed from: sortArray--nroSd4  reason: not valid java name */
    public static final void m471sortArraynroSd4(long[] array, int i, int i2) {
        Intrinsics.checkNotNullParameter(array, "array");
        m467quickSortnroSd4(array, i, i2 - 1);
    }
}
