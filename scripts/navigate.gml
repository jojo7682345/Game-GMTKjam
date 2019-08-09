///navigate(target)
var target = argument0;

if(game.timer <= 0){
    if(target==noone){
        exit;
    }
    if(mp_grid_path(game.map,path,x,y,target.x,target.y,true)){
        var toX = (path_get_point_x(path,0)+path_get_point_x(path,1))/2;
        var toY = (path_get_point_y(path,0)+path_get_point_y(path,1))/2;
        var dir = point_direction(x,y,toX,toY);
        switch(round(dir/45)*45){
            case 0:
            image_index = 2;
            image_xscale = 1;
            break;
            case 45:
            image_index = 3;
            image_xscale = 1;
            break;
            case 90:
            image_index = 4;
            image_xscale = 1;
            break;
            case 135:
            image_index = 3;
            image_xscale = -1;
            break;
            case 180:
            image_index = 2;
            image_xscale = -1;
            break;
            case 225:
            image_index = 1;
            image_xscale = -1;
            break;
            case 270:
            image_index = 0;
            image_xscale = 1;
            break;
            case 315:
            image_index = 1;
            image_xscale = 1;
            break;
            case 360:
            image_index = 2;
            image_xscale = 1;
            break;
        }
        x+= lengthdir_x(spd*delta_time/1000000,dir);
        y+= lengthdir_y(spd*delta_time/1000000,dir);
    }
}
