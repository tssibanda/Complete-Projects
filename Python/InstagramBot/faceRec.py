##!pip install DeepFace
from deepface import DeepFace
search_photo_path = "/Users/Thamsanqa/Documents/facerecPy"
def verification(img1, img2, model_name = "VGG-Face", distance_metric = "cosine"):
    start = time.time()
    result = DeepFace.verify(search_photo_path + img1,search_photo_path + img2, model_name = model_name, distance_metric = distance_metric)
    print(result)
    demography = DeepFace.analyze(search_photo_path + img1,['age', 'gender', 'Emotion'])
    print("Age :", demography["age"])
    print("Gender :", demography["gender"])
    print("Emotion :", demography["emotion"])
    end = time.time()
    print("Total Time Taken :", end - start)

verification(img1 = "2jpg", img2 = "3.jpg")