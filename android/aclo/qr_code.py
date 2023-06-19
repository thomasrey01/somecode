import glob
import cv2
import pathlib

file_path = "./image.jpeg"

def read_qr_code(filename):

    try:
        img = cv2.imread(filename)
        detect = cv2.QRCodeDetector()
        value, points, straight_qrcode = detect.detectAndDecode(img)
        return value
    except:
        return

print(read_qr_code(file_path))
