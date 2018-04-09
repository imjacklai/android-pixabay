package tw.jacklai.pixabay

import tw.jacklai.pixabay.model.Image
import tw.jacklai.pixabay.model.Response

class FakeData {
    companion object {
        fun getResponse(): Response {
            val total = 3
            val images = mutableListOf<Image>()

            images.add(Image(
                    3296399,
                    "https://pixabay.com/get/ea37b8092bfd083ed1584d05fb1d4e93e37fe5d310ac104497f3c37fa1e8b0ba_1280.jpg",
                    "https://pixabay.com/get/ea37b8092bfd083ed1584d05fb1d4e93e37fe5d310ac104497f3c37fa1e8b0ba_640.jpg",
                    640,
                    426,
                    "Couleur",
                    "https://cdn.pixabay.com/user/2018/02/20/17-49-25-926_250x250.jpg",
                    53,
                    21,
                    "japanese cherry trees, blossom, tree"
            ))

            images.add(Image(
                    3299525,
                    "https://pixabay.com/get/ea37b8062df6043ed1584d05fb1d4e93e37fe5d310ac104497f3c37fa1e8b0ba_1280.jpg",
                    "https://pixabay.com/get/ea37b8062df6043ed1584d05fb1d4e93e37fe5d310ac104497f3c37fa1e8b0ba_640.jpg",
                    640,
                    426,
                    "hansbenn",
                    "https://cdn.pixabay.com/user/2016/12/13/22-15-04-376_250x250.jpg",
                    26,
                    8,
                    "swan, chicken, swan kücken"
            ))

            images.add(Image(
                    3294681,
                    "https://pixabay.com/get/ea37b80b2efc003ed1584d05fb1d4e93e37fe5d310ac104497f3c37fa1e8b0ba_1280.jpg",
                    "https://pixabay.com/get/ea37b80b2efc003ed1584d05fb1d4e93e37fe5d310ac104497f3c37fa1e8b0ba_640.jpg",
                    640,
                    360,
                    "jplenio",
                    "https://cdn.pixabay.com/user/2018/01/09/20-18-07-389_250x250.jpg",
                    69,
                    49,
                    "nature, forest, sun"
            ))

            return Response(total, images)
        }
    }
}