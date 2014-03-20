function [J, grad] = costFunctionReg(theta, X, y, lambda)
%COSTFUNCTIONREG Compute cost and gradient for logistic regression with regularization
%   J = COSTFUNCTIONREG(theta, X, y, lambda) computes the cost of using
%   theta as the parameter for regularized logistic regression and the
%   gradient of the cost w.r.t. to the parameters. 

% Initialize some useful values
m = length(y); % number of training examples
n = length(theta);

% You need to return the following variables correctly 
J = 0;
grad = zeros(size(theta));

% ====================== YOUR CODE HERE ======================
% Instructions: Compute the cost of a particular choice of theta.
%               You should set J to the cost.
%               Compute the partial derivatives and set grad to the partial
%               derivatives of the cost w.r.t. each parameter in theta
for i = 1:m
  h(i) = sigmoid((theta')*(X(i,:)'));
  soma1(i) = ((-y(i))*log(h(i))) - ((1-y(i))*log(1-h(i)));
end
soma2(1) = 0;
for j = 2:n
  soma2(j) = (theta(j)^2);
end
J = (1/m)*sum(soma1) + (lambda/(2*m))*sum(soma2);

for j = 1:n
  for i = 1:m
    soma3(i,j) = (h(i)-y(i))*X(i,j);
  end
end

grad(1) = (1/m)*sum(soma3(:,1));
for j = 2:n
  grad(j) = (1/m)*sum(soma3(:,j)) + (lambda/m)*theta(j);
end


% =============================================================

end
