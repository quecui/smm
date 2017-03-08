var gulp = require('gulp'),
    watch = require('gulp-watch'),
    connect = require('gulp-connect');

gulp.task('webserver', function() {
    connect.server({
        port: 9000,
        livereload: true
    });
});

gulp.task('livereload', function() {
    gulp.src(['**/*.html'])
        .pipe(watch('**/*.html'))
        .pipe(watch('src/app/**/*.js'))
        .pipe(watch('src/**/*.css'))
        .pipe(connect.reload());
});

gulp.task('default', ['webserver', 'livereload']);