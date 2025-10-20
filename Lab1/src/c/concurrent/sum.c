#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

int static total = 0;
pthread_mutex_t lock;

void* do_sum(void* path) {
    char* n_path = (char*) path;
    FILE *file = fopen(n_path, "rb");  // open in binary mode
    if (file == NULL) {
        return NULL;  // indicate error
    }

    int sum = 0;
    int byte;

    while ((byte = fgetc(file)) != EOF) {
        sum += byte;
    }

    fclose(file);

    pthread_mutex_lock(&lock);
    total += sum;
    if (sum >= 0) {
        printf("%s : %d\n", n_path, sum);
    }
    pthread_mutex_unlock(&lock);


    return NULL;
}

int main(int argc, char *argv[]) {
    pthread_t threads[argc - 1];
    pthread_mutex_init(&lock, NULL);

    for (int i = 1; i < argc; i++) {
        const char *path = argv[i];
        pthread_create(&threads[i - 1], NULL, do_sum,(void*) path);
    }

    for (int i = 1; i < argc; i++)
    {
        pthread_join(threads[i - 1], NULL);
    }

    printf("Total: %d\n", total);

    pthread_mutex_destroy(&lock);
    return 0;
}
