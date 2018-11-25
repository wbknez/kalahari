local bounds = luajava.bindClass("com.solsticesquared.kalahari.math.Bounds")
local color = luajava.bindClass("com.solsticesquared.kalahari.math.color.Color3")
local image = luajava.bindClass("com.solsticesquared.kalahari.render.ImageOutput")
local order = luajava.bindClass("com.solsticesquared.kalahari.render.order.NaturalDrawingOrder")
local pipeline = luajava.bindClass("com.solsticesquared.kalahari.render.Pipeline")
local tracer = luajava.bindClass("com.solsticesquared.kalahari.render.Tracer")

-- Variables.
local black = luajava.newInstance(
    "com.solsticesquared.kalahari.math.color.Color3",
    0.0, 0.0, 0.0
)

local nord = luajava.newInstance(
    "com.solsticesquared.kalahari.render.order.NaturalDrawingOrder"
)

local out = luajava.newInstance(
    "com.solsticesquared.kalahari.render.ImageOutput",
    "test", image.Format.JPG
)

local pipe = luajava.newInstance(
    "com.solsticesquared.kalahari.render.Pipeline"
)

local red = luajava.newInstance(
    "com.solsticesquared.kalahari.math.color.Color3",
    1.0, 0.0, 0.0
)

local view = luajava.newInstance(
    "com.solsticesquared.kalahari.math.Bounds",
    800, 800
)

local trace_it = luajava.createProxy("com.solsticesquared.kalahari.render.Tracer", {
    trace = function(coords)
        local x = coords:getX()
        local y = coords:getY()

        if x > 50 and x < 750 and y > 50 and y < 750 then
            return red
        else
            return black
        end
    end
})

pipe:addListener(out)
pipe:run(trace_it, view, nord)
